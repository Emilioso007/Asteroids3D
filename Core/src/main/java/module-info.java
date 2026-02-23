module Core {
    requires javafx.graphics;
    requires javafx.controls;
    requires Common;
    requires RenderingSystem;
    requires MovementSystem;
    requires RotateSystem;
    requires InputSystem;
    requires AsteroidEntity;
    requires PlayerEntity;
    requires CollisionSystem;
    requires ShootSystem;
    requires BulletEntity;
    requires OutOfBoundsSystem;
    requires AsteroidBulletCollisionResponseSystem;
    requires AsteroidPlayerCollisionResponseSystem;
    exports io.asteroidsfx;
}