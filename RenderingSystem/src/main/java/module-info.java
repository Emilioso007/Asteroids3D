import io.asteroidsfx.common.system.SystemECS;

module RenderingSystem {
    exports io.asteroidsfx.renderingsystem;
    requires Common;
    requires RenderComponent;
    requires PositionComponent;
    requires RotationComponent;
    requires AngleComponent;
    requires javafx.graphics;
    provides SystemECS with io.asteroidsfx.renderingsystem.RenderingSystem;
}