import io.asteroidsfx.common.system.SystemECS;

module OutOfBounds {
    exports io.asteroidsfx.outofbounds;
    requires AngleComponent;
    requires Common;
    requires PositionComponent;
    requires VelocityComponent;

    provides SystemECS with io.asteroidsfx.outofbounds.OutOfBoundsSystem;
}