import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.outofbounds.OutOfBoundsSystem;

module OutOfBounds {
    requires Common;
    requires CommonPhysics3D;

    provides BaseSystem with OutOfBoundsSystem;
}