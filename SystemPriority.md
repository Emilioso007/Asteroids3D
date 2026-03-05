# System priorities 
(Lower values are processed earlier)

0: [LifetimeSystem](LifetimeModule/src/main/java/io/asteroidsjaylib/lifetime/LifetimeSystem.java) - Sets toBeRemoved on entities after some time

5: [PlayerMovementSystem](PlayerModule/src/main/java/io/asteroidsjaylib/player/PlayerMovementSystem.java) - Listens to keyboard input and controls player

10: [PlayerShootingSystem](PlayerModule/src/main/java/io/asteroidsjaylib/player/PlayerShootingSystem.java) - Listens to keyboard and publish spawn event

20: [AccelerationSystem](PhysicsModule/src/main/java/io/asteroidsjaylib/physics/system/AccelerationSystem.java) - Applies acceleration force to velocity

21: [DragSystem](PhysicsModule/src/main/java/io/asteroidsjaylib/physics/system/DragSystem.java) - Applies drag to velocity

22: [VelocitySystem](PhysicsModule/src/main/java/io/asteroidsjaylib/physics/system/VelocitySystem.java) - Applies velocity to position

25: [OutOfBoundsSystem](OutOfBoundsModule/src/main/java/io/asteroidsjaylib/outofbounds/OutOfBoundsSystem.java) - Wraps entities based on position

30: [EnemySystem](EnemyModule/src/main/java/io/asteroidsjaylib/enemy/EnemySystem.java) - Shoots at player

30: [RotationSystem](PhysicsModule/src/main/java/io/asteroidsjaylib/physics/system/RotationSystem.java) - Applies rotation to angle

70: [CollisionSystem](CollisionModule/src/main/java/io/asteroidsjaylib/collision/CollisionSystem.java) - Publishes collision events

100: [RenderingSystem](RenderingSystem/src/main/java/io/asteroidsjaylib/renderingsystem/RenderingSystem.java) - Renders entities


xx: [AsteroidCollisionResponseSystem](AsteroidModule/src/main/java/io/asteroidsjaylib/asteroid/AsteroidCollisionResponseSystem.java) - Listens to collision events

xx: [BulletCollisionResponseSystem](BulletModule/src/main/java/io/asteroidsjaylib/bullet/BulletCollisionResponseSystem.java) - Listens to collision events

xx: [EnemyCollisionResponseSystem](EnemyModule/src/main/java/io/asteroidsjaylib/enemy/EnemyCollisionResponseSystem.java) - Listens to collision events

xx: [PlayerCollisionResponseSystem](PlayerModule/src/main/java/io/asteroidsjaylib/player/PlayerCollisionResponseSystem.java) - Listens to collision events

xx: [SpawnSystem](SpawnModule/src/main/java/io/asteroidsjaylib/spawn/SpawnSystem.java) - Listens to spawn events