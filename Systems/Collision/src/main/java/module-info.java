import io.asteroidsjaylib.collision.Collision3DSystem;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Collision {
    requires Common;
    requires CommonCollision;
    requires CommonPhysics3D;

    provides BaseSystem with Collision3DSystem;
}