import io.asteroidsfx.common.system.SystemECS;

module Collision {
    exports io.asteroidsfx.collision;
    requires Common;
    requires PositionComponent;

    provides SystemECS with io.asteroidsfx.collision.CollisionSystem;
}