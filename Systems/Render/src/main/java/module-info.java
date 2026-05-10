import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.render.LODSystem;
import io.asteroidsjaylib.render.RenderSystem;

module Render {
    requires io.github.electronstudio.jaylib.ffm;
    requires Common;
    requires CommonRender;
    requires CommonPhysics3D;
    requires CommonPlayer;

    provides BaseSystem with RenderSystem, LODSystem;
}