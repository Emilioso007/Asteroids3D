import io.asteroidsjaylib.common.ecs.BaseSystem;

module OutOfBounds {
    exports io.asteroidsjaylib.outofbounds;
    requires Common;
    requires Physics;

    provides BaseSystem with io.asteroidsjaylib.outofbounds.OutOfBoundsSystem;
}