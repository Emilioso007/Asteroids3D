import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.physics.system.AccelerationSystem;
import io.asteroidsjaylib.physics.system.DragSystem;
import io.asteroidsjaylib.physics.system.RotationSystem;
import io.asteroidsjaylib.physics.system.VelocitySystem;

module Physics {
    exports io.asteroidsjaylib.physics.component;
    exports io.asteroidsjaylib.physics.system;
    requires Common;

    provides BaseSystem with AccelerationSystem, DragSystem, RotationSystem, VelocitySystem;
}