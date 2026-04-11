import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.render.RenderSystem;

module Render {
    requires jaylib;
    requires Common;
    requires CommonRender;
    requires CommonPhysics2D;
    requires CommonCollision;

    provides BaseSystem with RenderSystem;
}