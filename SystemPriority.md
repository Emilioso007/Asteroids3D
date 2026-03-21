# System priorities 
(Lower values are processed earlier)

0: [LifetimeSystem](Systems/Lifetime/src/main/java/io/asteroidsjaylib/lifetime/LifetimeSystem.java) - Sets toBeRemoved on entities after some time

5: [PlayerMovementSystem](Entities/Player/src/main/java/io/asteroidsjaylib/player/PlayerMovementSystem.java) - Listens to keyboard input and controls player

10: [PlayerShootingSystem](Entities/Player/src/main/java/io/asteroidsjaylib/player/PlayerShootingSystem.java) - Listens to keyboard and publish spawn event

20: [AccelerationSystem](Systems/Physics/src/main/java/io/asteroidsjaylib/physics/AccelerationSystem.java) - Applies acceleration force to velocity

21: [DragSystem](Systems/Physics/src/main/java/io/asteroidsjaylib/physics/DragSystem.java) - Applies drag to velocity

22: [VelocitySystem](Systems/Physics/src/main/java/io/asteroidsjaylib/physics/VelocitySystem.java) - Applies velocity to position

25: [OutOfBoundsSystem](Systems/OutOfBounds/src/main/java/io/asteroidsjaylib/outofbounds/OutOfBoundsSystem.java) - Wraps entities based on position

30: [EnemySystem](Entities/Enemy/src/main/java/io/asteroidsjaylib/enemy/EnemySystem.java) - Shoots at player

30: [RotationSystem](Systems/Physics/src/main/java/io/asteroidsjaylib/physics/RotationSystem.java) - Applies rotation to angle

70: [CollisionSystem](Systems/Collision/src/main/java/io/asteroidsjaylib/collision/CollisionSystem.java) - Publishes collision events

100: [RenderingSystem](Systems/Render/src/main/java/io/asteroidsjaylib/render/RenderSystem.java) - Renders entities


xx: [AsteroidCollisionResponseSystem](Entities/Asteroid/src/main/java/io/asteroidsjaylib/asteroid/AsteroidCollisionResponseSystem.java) - Listens to collision events

xx: [BulletCollisionResponseSystem](Entities/Bullet/src/main/java/io/asteroidsjaylib/bullet/BulletCollisionResponseSystem.java) - Listens to collision events

xx: [EnemyCollisionResponseSystem](Entities/Enemy/src/main/java/io/asteroidsjaylib/enemy/EnemyCollisionResponseSystem.java) - Listens to collision events

xx: [PlayerCollisionResponseSystem](Entities/Player/src/main/java/io/asteroidsjaylib/player/PlayerCollisionResponseSystem.java) - Listens to collision events

xx: [SpawnSystem](Systems/Spawn/src/main/java/io/asteroidsjaylib/spawn/SpawnSystem.java) - Listens to spawn events
