import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.render.RenderSystem;

module Render {
    exports io.asteroidsjaylib.render.shapes;
    exports io.asteroidsjaylib.render;
    exports io.asteroidsjaylib.render.component;
    requires jaylib;
    requires Physics;
    requires Common;

    provides BaseSystem with RenderSystem;
}