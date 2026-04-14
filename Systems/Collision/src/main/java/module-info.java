import io.asteroidsjaylib.collision.Collision3DSystem;
import io.asteroidsjaylib.collision.CollisionSystem;
import io.asteroidsjaylib.collision.MouseCollisionSystem;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Collision {
    requires Common;
    requires CommonPhysics2D;
    requires CommonCollision;
    requires CommonButton;
    requires CommonRender;
    requires CommonPhysics3D;

    provides BaseSystem with CollisionSystem, MouseCollisionSystem, Collision3DSystem;
}