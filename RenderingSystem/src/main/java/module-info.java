import io.asteroidsfx.common.ecs.BaseSystem;

module RenderingSystem {
    exports io.asteroidsfx.renderingsystem;
    requires Common;
    requires RenderComponent;
    requires PositionComponent;
    requires AngleComponent;
    requires javafx.graphics;
    provides BaseSystem with io.asteroidsfx.renderingsystem.RenderingSystem;
}