import io.asteroidsjaylib.common.ecs.BaseSystem;

module OutOfBounds {
    requires Common;
    requires CommonPhysics2D;
    requires CommonPhysics3D;

    provides BaseSystem with io.asteroidsjaylib.outofbounds.OutOfBoundsSystem;
}