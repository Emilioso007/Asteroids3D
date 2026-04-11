import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.physics.AccelerationSystem;
import io.asteroidsjaylib.physics.DragSystem;
import io.asteroidsjaylib.physics.RotationSystem;
import io.asteroidsjaylib.physics.VelocitySystem;

module Physics {
    requires Common;
    requires CommonPhysics2D;

    provides BaseSystem with AccelerationSystem, DragSystem, RotationSystem, VelocitySystem;
}