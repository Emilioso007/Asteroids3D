import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.render.RenderSystem;

module Render {
    requires jaylib;
    requires Common;
    requires CommonRender;
    requires CommonPhysics3D;
    requires CommonPlayer;

    provides BaseSystem with RenderSystem;
}