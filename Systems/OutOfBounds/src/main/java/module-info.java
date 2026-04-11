import io.asteroidsjaylib.common.ecs.BaseSystem;

module OutOfBounds {
    requires Common;
    requires CommonPhysics2D;
    requires CommonOutOfBounds;

    provides BaseSystem with io.asteroidsjaylib.outofbounds.OutOfBoundsSystem;
}