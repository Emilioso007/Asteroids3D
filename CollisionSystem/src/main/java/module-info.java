import io.asteroidsfx.collisionsystem.CollisionSystemProvider;
import io.asteroidsfx.common.SystemSpi;

module CollisionSystem {
    exports io.asteroidsfx.collisionsystem;
    requires Common;
    requires CircleColliderComponent;
    requires PositionComponent;

    provides SystemSpi with CollisionSystemProvider;
}