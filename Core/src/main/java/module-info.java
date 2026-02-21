module Core {
    requires javafx.graphics;
    requires javafx.controls;
    requires Common;
    requires RenderingSystem;
    requires MovementSystem;
    requires AsteroidEntity;
    requires WraparoundSystem;
    requires RotateSystem;
    exports io.asteroidsfx;
}