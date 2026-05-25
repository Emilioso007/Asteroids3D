= 5. Implementation <implementation>

In the following section, the implementation of the system will be documented.

== 5.1 Modular Encapsulation & Dependencies

To ensure strong encapsulation and reliable dependencies, the Java Platform Module System (JPMS) is used to dictate module accessibility and visibility across the entire system. This is required by NF01, NF02, NF03, and NF07.


#figure(
    ```java

    module Asteroid {
        requires Common;
        requires CommonAsteroid;
        requires CommonCollision;
        requires CommonCrystal;
        requires CommonEnemy;
        requires CommonLifetime;
        requires CommonPhysics3D;
        requires CommonRender;
        requires CommonSpawn;
        requires spring.beans;
        requires spring.context;

        opens io.asteroidsjaylib.asteroid to spring.core, spring.context, spring.beans;

        uses AsteroidSPI;
        uses CrystalSPI;

        provides AsteroidSPI with AsteroidProvider;
        provides BaseSystem with AsteroidCollisionResponseSystem;
    }

    ```,
    caption: [Asteroid module-info file. Imports are excluded.],
    supplement: [Code Snippet]
) <asteroid-module-info>

#ref(<asteroid-module-info>) shows the module-info.java file for the Asteroid module. This module contains the AsteroidProvider, AsteroidEntity, and AsteroidCollisionResponseSystem classes.

The module requires a variety of Common modules. These requires directives tell what other modules must be present for this module to compile and run. This can be seen when compiling the game, where the Common module is the first to get compiled, followed by the other Common modules, and finally the specific implementations.

The opens keyword is used to allow other modules to use reflection within the Asteroid module. Here some sub-packages of the Spring framework are allowed such access. This is required because of the usage of Spring’s ApplicationEventPublisher and \@EventListener used for event-driven communication throughout the game.

The uses directive signals that this module will act as a consumer, allowing the ServiceLoader to discover external implementations of the specified types. This is here to allow the collision response class to spawn new asteroids and crystals on hit, through the service provider interface implementations.

Finally, the provides ... with syntax specifies that this module acts as a service provider. It exposes its internal AsteroidProvider and AsteroidCollisionResponseSystem to any module that uses the AsteroidSPI and BaseSystem types, respectively.

Ultimately, this configuration guarantees that no other module can access the internal implementations of the Asteroid module. By restricting access to everything except the Spring framework and the explicit SPIs, the system achieves strong encapsulation and reliable dependencies.


#figure(
    ```java

    import io.asteroidsjaylib.common.ecs.BaseSystem;
    import io.asteroidsjaylib.common.ecs.EntitySpi;

    module Core {
        requires Common;
        requires io.github.electronstudio.jaylib.ffm;
        requires spring.beans;
        requires spring.context;

        opens io.asteroidsjaylib to spring.core, spring.beans, spring.context;

        uses BaseSystem;
        uses EntitySPI;
    }

    ```,
    caption: [Core module-info file.],
    supplement: [Code Snippet]
) <core-module-info>

#ref(<core-module-info>) shows the module-info.java file for the Core module. Here it is worth noting that it only requires the standard common module, and nothing else. It also declares that it uses the BaseSystem class implementations, as well as any EntitySPIs, like the Player. This allows the Core to discover and use the implementing modules through these types, without any dependency on them at compile-time.

#pagebreak()
== 5.2 Component Registration & Access

To successfully integrate the decoupled modules at runtime, the system uses a hybrid discovery and injection pattern utilizing both the Java ServiceLoader and the Spring Framework. This satisfies requirements NF01, NF02, NF03, and NF08.


#figure(
    ```java

    @Configuration
    @ComponentScan(basePackages = "io.asteroidsjaylib")
    public class AppConfig {

        @Autowired
        private ConfigurableListableBeanFactory beanFactory;

        @Bean
        public List<BaseSystem> baseSystems() {
            List<BaseSystem> systems = new ArrayList<>();
            for (BaseSystem system : ServiceLoader.load(BaseSystem.class)) {

                String beanName = system.getClass().getName();

                beanFactory.registerSingleton(beanName, system);
                beanFactory.autowireBean(system);
                beanFactory.initializeBean(system, beanName);
                systems.add(system);
            }
            return systems;
        }

        @Bean
        public List<EntitySPI> entitySpis() {
            /* Similar logic as above */
        }

    }

    ```,
    caption: [Appconfig class],
    supplement: [Code Snippet]
) <appconfig-class>

#ref(<appconfig-class>) shows the AppConfig class, which is annotated with \@Configuration. This class acts as the bridge between JPMS module discovery and the Spring Inversion of Control (IoC) container. Because the external modules are completely decoupled from the Core, Spring cannot scan their packages directly.
To circumvent this, the configuration autowires a ConfigurableListableBeanFactory. Within the bean producer methods, the Java ServiceLoader is used to dynamically discover all provided implementations of BaseSystem and EntitySPI on the module path. Once discovered, these instances are registered with the Spring factory as singletons based on their class names. Spring then autowires and initializes these dynamically loaded classes, bringing them fully under the management of the Spring context.


#figure(
    ```java

    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Game game = context.getBean(Game.class);
        game.start();
        context.close();
    }

    @Autowired
    public Game(List<BaseSystem> systems, List<EntitySPI> entitySPIS) {
        this.systems = systems;
        this.entitySPIS = entitySPIS;
    }

    ```,
    caption: [Main method entrypoint and Game constructor.],
    supplement: [Code Snippet]
) <main-and-game>

With the components registered, the application entry point (see #ref(<main-and-game>)) initializes the AnnotationConfigApplicationContext using AppConfig.class. It then requests the primary Game bean.
The Game class, annotated with \@Component, uses constructor injection, explicitly requiring a List<BaseSystem> and a List<EntitySPI> as its arguments. Because the AppConfig registered all discovered plugins into the application context, Spring satisfies these dependencies. This now allows the Game to add the systems and entities to the World, without having to use the ServiceLoader directly.

#pagebreak()
== 5.3 ECS Component Models

To fulfill the data-oriented design requirement (NF04), the implementation separates state from behavior. The following sections explain how components are registered to entities, how systems query these entities based on components, and how the core optimizes this access at runtime.

=== Component Registration
Within the ECS architecture, entities serve as data containers. They are constructed by dynamically attaching the relevant data components during instantiation.

#figure(
    ```java

    public class AsteroidEntity extends BaseEntity {
        public AsteroidEntity(Vector3D startPosition, Vector3D startVelocity, /* ... */) {
            this.add(new Position().vector(startPosition));
            this.add(new Velocity().vector(startVelocity));
            // Additional component registration...
        }
    }

    ```,
    caption: [Component registration within the AsteroidEntity constructor.],
    supplement: [Code Snippet]
) <asteroid-constructor>

As illustrated in #ref(<asteroid-constructor>), an AsteroidEntity is populated with Position and Velocity components. The add() method registers these components internally within the BaseEntity, making the data available for system queries.

=== System Logic and Component Access
Once components are registered to entities within the World, the specialized systems execute their domain logic by accessing this data.

#figure(
    ```java

    public class VelocitySystem extends IteratingSystem {

        @Override
        public void start(IWorld world) {
            this.priority(22);
        }

        @Override
        public List<Class<? extends BaseComponent>> signature() {
            return List.of(Position.class, Velocity.class);
        }

        @Override
        public void update(IWorld world, BaseEntity entity, float deltaTime) {
            Position position = entity.get(Position.class);
            Velocity velocity = entity.get(Velocity.class);

            assert position != null;
            assert velocity != null;

            position.vector().addScaled(velocity.vector(), deltaTime);
        }
    }

    ```,
    caption: [The VelocitySystem defining its signature and mutation loop.],
    supplement: [Code Snippet]
) <velocity-system>

#ref(<velocity-system>) shows the VelocitySystem, which extends the IteratingSystem class. The signature() method defines the system's pre-condition: It requires entities to have both Position and Velocity components. The World uses this signature to query for matching entities. Because it is an iterating system, the update() method processes these entities sequentially, fetching the specific components via entity.get() and mutating the data by applying the velocity vector scaled by deltaTime.

=== Query Optimization and Caching
Continuously querying all entities against every system signature every frame introduces computational overhead ($O(N times M)$ complexity). To ensure the game loop remains performant, the World class implements an optimized caching mechanism.

#figure(
    ```java

    private final Map<BaseSystem, List<BaseEntity>> systemEntityCache = new HashMap<>();

    private void updateCacheIfNeeded() {
        boolean entitiesChanged = entities.removeIf(BaseEntity::removed);

        if (!entitiesToAdd.isEmpty()){
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
            entitiesChanged = true;
        }

        if (entitiesChanged || systemEntityCache.isEmpty()){
            for (BaseSystem system : systems){
                List<Class<? extends BaseComponent>> signature = system.signature();

                List<BaseEntity> matching = systemEntityCache.computeIfAbsent(system, _ -> new ArrayList<>());
                matching.clear();

                if (signature != null && !signature.isEmpty()){
                    for (BaseEntity entity : entities){
                        if (entity.hasAll(signature)) matching.add(entity);
                    }
                }
                systemEntityCache.put(system, matching);
            }
        }
    }

    ```,
    caption: [Entity cache recalculation logic within the World object.],
    supplement: [Code Snippet]
) <cache-update-method>


#ref(<cache-update-method>) shows the updateCacheIfNeeded method, which manages the Map\<BaseSystem, List\<BaseEntity>>. Rather than querying components on every frame, the cache is only invalidated and recalculated when a change occurs. This happens when entities are marked for removal or new entities are added to the world. If a change is detected, the method iterates through each registered system, compares the active entities against the system's signature(), and repopulates the cached lists. This caching strategy prevents the world from querying all entities every frame, ensuring that the system updates remain highly efficient.

#pagebreak()
== 5.4 Microservice Integration

To fulfill the external service requirement (NF05), the game's scoring mechanic is decoupled from the main client and managed by an external microservice. This ensures the score state can be persisted and monitored independently of the game process.


=== The Standalone Scoring Service
The backend is implemented as a Spring Boot application exposing a RESTful API.

#figure(
    ```java

    @SpringBootApplication
    @RestController
    public class ScoringService {

        private int score = 0;

        public static void main(String[] args) {
            SpringApplication.run(ScoringService.class, args);
        }

        @GetMapping("/score")
        public int score() {
            return score;
        }

        @PostMapping("/score")
        public void score(@RequestParam(value = "increment") int increment) {
            score += increment;
        }
    }

    ```,
    caption: [The standalone Spring Boot REST microservice.],
    supplement: [Code Snippet]
) <scoring-service>

As seen in #ref(<scoring-service>), the service maintains the accumulated score state and exposes HTTP GET and POST endpoints to retrieve and increment the value.


=== The Client-Side Event Listener
When a crystal is collected, a ScoreEvent is published to the event bus.

#figure(
    ```java

    public class ScoreSystem extends ResponseSystem {

        private final String scoringServiceUrl = "http://localhost:8080/score";
        private final RestTemplate restTemplate = new RestTemplate();

        @EventListener
        private void handleScoreIncrement(ScoreEvent scoreEvent){
            String url = scoringServiceUrl + "?increment=" + scoreEvent.increment;

            // Execute asynchronously to prevent blocking the game loop
            CompletableFuture.runAsync(() -> {
                try {
                    restTemplate.postForLocation(url, null);
                } catch (RestClientException e) {
                    System.out.println("Score Service not responding: " + e.getMessage());
                }
            });
        }
    }

    ```,
    caption: [The client-side ScoreSystem utilizing asynchronous HTTP requests.],
    supplement: [Code Snippet]
) <score-system>

#ref(<score-system>) illustrates the ScoreSystem, which listens for these events. A critical feature of this implementation is the use of the runAsync method. Because network latency is highly unpredictable, executing synchronous HTTP requests could freeze the main game loop and ruin the user experience. Wrapping the logic inside a runAsync call offloads the network I/O to a separate worker thread, allowing it to complete independently. Furthermore, to ensure graceful degradation, the postForLocation call is wrapped in a try-catch block. If the external microservice is offline or unresponsive, the system safely catches the RestClientException, preventing a fatal crash and allowing the game to proceed normally.


























