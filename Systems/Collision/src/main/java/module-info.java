import io.asteroidsjaylib.collision.CollisionSystem;
import io.asteroidsjaylib.collision.MouseCollisionSystem;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Collision {
    requires Common;
    requires CommonPhysics2D;
    requires CommonCollision;
    requires CommonButton;
    requires CommonRender;
    requires jdk.jshell;

    provides BaseSystem with CollisionSystem, MouseCollisionSystem;
}