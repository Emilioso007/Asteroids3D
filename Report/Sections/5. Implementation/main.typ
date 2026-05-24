= 5. Implementation <implementation>

In the following section, the implementation of the system will be documented.

== 5.1 Modular Encapsulation & Dependencies

To ensure strong encapsulation and reliable dependencies, the Java Platform Module System (JPMS) is used to dictate module accessibility and visibility across the entire system. This is required by #link(<NF01>)[NF01], #link(<NF02>)[NF02], #link(<NF03>)[NF03], and #link(<NF07>)[NF07].


#include "Resources/asteroid-module-info.typ"

#ref(<asteroid-module-info>) shows the module-info.java file for the Asteroid module. This module contains the AsteroidProvider, AsteroidEntity, and AsteroidCollisionResponseSystem classes.

The module requires a variety of Common modules. These requires directives tell what other modules must be present for this module to compile and run. This can be seen when compiling the game, where the Common module is the first to get compiled, followed by the other Common modules, and finally the specific implementations.

The opens keyword is used to allow other modules to use reflection within the Asteroid module. Here some sub-packages of the Spring framework are allowed such access. This is required because of the usage of Spring’s ApplicationEventPublisher and \@EventListener used for event-driven communication throughout the game.

The uses directive signals that this module will act as a consumer, allowing the ServiceLoader to discover external implementations of the specified types. This is here to allow the collision response class to spawn new asteroids and crystals on hit, through the service provider interface implementations.

Finally, the provides ... with syntax specifies that this module acts as a service provider. It exposes its internal AsteroidProvider and AsteroidCollisionResponseSystem to any module that uses the AsteroidSPI and BaseSystem types, respectively.

Ultimately, this configuration guarantees that no other module can access the internal implementations of the Asteroid module. By restricting access to everything except the Spring framework and the explicit SPIs, the system achieves strong encapsulation and reliable dependencies.


#include "Resources/core-module-info.typ"

#ref(<core-module-info>) shows the module-info.java file for the Core module. Here it is worth noting that it only requires the standard common module, and nothing else. It also declares that it uses the BaseSystem class implementations, as well as any EntitySPIs, like the Player. This allows the Core to discover and use the implementing modules through these types, without any dependency on them at compile-time.


== 5.2 Component Registration & Access

To successfully integrate the decoupled modules at runtime, the system uses a hybrid discovery and injection pattern utilizing both the Java ServiceLoader and the Spring Framework. This satisfies requirements #link(<NF01>)[NF01], #link(<NF02>)[NF02], #link(<NF03>)[NF03], and #link(<NF08>)[NF08].


#include "Resources/appconfig.typ"

#ref(<appconfig-class>) shows the AppConfig class, which is annotated with \@Configuration. This class acts as the bridge between JPMS module discovery and the Spring Inversion of Control (IoC) container. Because the external modules are completely decoupled from the Core, Spring cannot scan their packages directly.
To circumvent this, the configuration autowires a ConfigurableListableBeanFactory. Within the bean producer methods, the Java ServiceLoader is used to dynamically discover all provided implementations of BaseSystem and EntitySPI on the module path. Once discovered, these instances are registered with the Spring factory as singletons based on their class names. Spring then autowires and initializes these dynamically loaded classes, bringing them fully under the management of the Spring context.


#include "Resources/main-and-game.typ"

With the components registered, the application entry point (see #ref(<main-and-game>)) initializes the AnnotationConfigApplicationContext using AppConfig.class. It then requests the primary Game bean.
The Game class, annotated with \@Component, uses constructor injection, explicitly requiring a List<BaseSystem> and a List<EntitySPI> as its arguments. Because the AppConfig registered all discovered plugins into the application context, Spring satisfies these dependencies. This now allows the Game to add the systems and entities to the World, without having to use the ServiceLoader directly.
