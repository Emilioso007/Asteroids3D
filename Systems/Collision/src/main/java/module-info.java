import io.asteroidsjaylib.collision.CollisionSystem;
import io.asteroidsjaylib.collision.MouseCollisionSystem;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Collision {
    requires Common;
    requires CommonPhysics;
    requires CommonCollision;
    requires CommonButton;
    requires CommonRender;

    provides BaseSystem with CollisionSystem, MouseCollisionSystem;
}