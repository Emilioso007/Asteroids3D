import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.physics3d.AccelerationSystem;
import io.asteroidsjaylib.physics3d.DragSystem;
import io.asteroidsjaylib.physics3d.VelocitySystem;

module Physics3D {
    requires Common;
    requires CommonPhysics3D;

    provides BaseSystem with VelocitySystem, AccelerationSystem, DragSystem;
}