import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.render.RenderSystem;

module Render {
    requires jaylib;
    requires Common;
    requires CommonRender;
    requires CommonPhysics;

    provides BaseSystem with RenderSystem;
}