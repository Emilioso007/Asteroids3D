import io.asteroidsjaylib.common.ecs.BaseSystem;

module Collision {
    exports io.asteroidsjaylib.collision;
    requires Common;
    requires Physics;

    provides BaseSystem with io.asteroidsjaylib.collision.CollisionSystem;
}