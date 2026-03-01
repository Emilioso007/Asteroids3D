import io.asteroidsfx.common.ecs.BaseSystem;
import io.asteroidsfx.physics.system.AccelerationSystem;
import io.asteroidsfx.physics.system.DragSystem;
import io.asteroidsfx.physics.system.RotationSystem;
import io.asteroidsfx.physics.system.VelocitySystem;

module Physics {
    exports io.asteroidsfx.physics.component;
    exports io.asteroidsfx.physics.system;
    requires Common;

    provides BaseSystem with AccelerationSystem, DragSystem, RotationSystem, VelocitySystem;
}