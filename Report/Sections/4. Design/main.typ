= 4. Design <design>

A proper design is crucial in a component-based system. This section will dive into what choices have been made to fulfill all relevant requirements.
Complying with requirement NF04, the system is designed in a data-oriented way by utilizing Entity-Component-System architecture traits.
Entities serve strictly as containers of various components. There exists zero logic on the individual entities, apart from their creation, which is minimal.
Components contain data in all shapes and sizes. This could be everything from just a singular boolean flag, to large meshes and materials.


#figure(
    image("Resources/Systems.png"),
    caption: [Four sub systems specializing in various patterns.]
) <figure-systems>

Systems query the world for entities that contain a collection of components the system is interested in. This is where all the logic is implemented. Different systems have different running characteristics. To accommodate this, four different specialized systems (see #ref(<figure-systems>)) have been created, on top of the BaseSystem.
The most common is the IteratingSystem. As the name suggests, it iterates through the list of entities matching its signature, one by one. In contrast to the BulkSystem that hands over the entire list of entities all at once, which in some cases is beneficial, for example in a collision detection system where comparison is necessary between entities. The IntervalIteratingSystem is an IteratingSystem on a configurable timer, only firing the update method once per interval. Finally, the ResponseSystem is a way to tell the game that I’m just here to listen to events.


#figure(
    image("Resources/ECS.png"),
    caption: [ECS Diagram.]
) <figure-ecs>

#ref(<figure-ecs>) illustrates how the components relate to each other. Notably, there is no direct connection between the Player and Physics modules, despite the Player being affected by physical calculations. This decoupling is achieved through shared contracts.
The Player extends the BaseEntity class, which resides in the World. The Physics module is a BaseSystem within that same World. To process entities, the World queries the Physics system through the BaseSystem abstraction to determine what components it expects. Having all the required components serves as the pre-condition for any entity to be processed by that system. The World then filters the entities and passes a list of matching ones to the Physics system.
The Position and Velocity components are declared in a common module, that both Physics and Player know about and rely on. This allows the Physics system to manipulate the Player’s position without having any dependency on the Player itself. The system’s post-condition is fulfilled once it has mutated the relevant component data of the entities.
This same principle is applied to all the different systems throughout the game. Another feature of the systems is the priority value. This allows certain systems to run before or after other systems. This is necessary in certain physics calculations where acceleration is applied before velocity. This is also used to ensure that rendering is always the last thing each frame, to make sure that the user is seeing the newest state of the game.


#figure(
    image("Resources/Component.png"),
    caption: [Partial Component Diagram.]
) <figure-component>

#ref(<figure-component>) shows the modular provides-requires flow of the system. The Player module provides an EntitySPI interface implementation that the Core module uses. Likewise, all the system modules, some of which are shown on the diagram, are providing BaseSystem realizations which the Core module also uses. Enemies and asteroids are controlled by a Spawn system. This requires an Enemy- and AsteroidSPI declared in their own common modules, CommonEnemy and CommonAsteroid respectively. Both Player and Enemy use the BulletSPI interface when shooting. The Asteroid module contains the system spawning crystals on hit, hence the requirement to the CrystalSPI.
The discovery between modules is utilizing the Java ServiceLoader pattern, along with internal Spring Dependency Injection. This allows the Core to discover and inject implementations at runtime without any hardcoded dependencies. This satisfies requirements NF01, NF02, NF03, NF07, and NF08.
The Scoring Service provides endpoints for incrementing and retrieving the score through a REST-based microservice, satisfying requirement NF05.


