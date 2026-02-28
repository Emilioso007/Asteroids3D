import io.asteroidsfx.common.ecs.BaseSystem;

module OutOfBounds {
    exports io.asteroidsfx.outofbounds;
    requires Common;
    requires PositionComponent;
    requires VelocityComponent;

    provides BaseSystem with io.asteroidsfx.outofbounds.OutOfBoundsSystem;
}