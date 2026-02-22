module Core {
    requires javafx.graphics;
    requires javafx.controls;
    requires Common;
    requires RenderingSystem;
    requires MovementSystem;
    requires WraparoundSystem;
    requires RotateSystem;
    requires InputSystem;
    requires AsteroidEntity;
    requires PlayerEntity;
    requires CollisionSystem;
    exports io.asteroidsfx;
}