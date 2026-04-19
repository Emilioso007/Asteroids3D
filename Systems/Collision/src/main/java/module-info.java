import io.asteroidsjaylib.collision.Collision3DSystem;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Collision {
    requires Common;
    requires CommonCollision;
    requires CommonPhysics3D;
    requires spring.beans;
    requires spring.context;

    opens io.asteroidsjaylib.collision to spring.core, spring.context, spring.beans;

    provides BaseSystem with Collision3DSystem;
}