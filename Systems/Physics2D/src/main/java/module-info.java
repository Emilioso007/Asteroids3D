import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.physics2d.AccelerationSystem;
import io.asteroidsjaylib.physics2d.DragSystem;
import io.asteroidsjaylib.physics2d.RotationSystem;
import io.asteroidsjaylib.physics2d.VelocitySystem;

module Physics2D {
    requires Common;
    requires CommonPhysics2D;

    provides BaseSystem with AccelerationSystem, DragSystem, RotationSystem, VelocitySystem;
}