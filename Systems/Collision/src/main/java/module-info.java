import io.asteroidsjaylib.collision.CollisionSystem;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Collision {
    requires Common;
    requires CommonPhysics;
    requires CommonCollision;

    provides BaseSystem with CollisionSystem;
}