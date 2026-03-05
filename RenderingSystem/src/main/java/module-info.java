import io.asteroidsjaylib.common.ecs.BaseSystem;

module RenderingSystem {
    exports io.asteroidsjaylib.renderingsystem;
    requires Common;
    requires RenderComponent;
    requires jaylib;
    requires Physics;
    provides BaseSystem with io.asteroidsjaylib.renderingsystem.RenderingSystem;
}