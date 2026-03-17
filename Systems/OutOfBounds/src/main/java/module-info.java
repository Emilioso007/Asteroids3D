import io.asteroidsjaylib.common.ecs.BaseSystem;

module OutOfBounds {
    requires Common;
    requires CommonPhysics;
    requires CommonOutOfBounds;

    provides BaseSystem with io.asteroidsjaylib.outofbounds.OutOfBoundsSystem;
}