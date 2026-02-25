import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.movementsystem.MovementSystemProvider;

module MovementSystem {
    requires Common;
    requires PositionComponent;
    requires VelocityComponent;
    requires LinearVelocityComponent;
    requires AngleComponent;
    requires DragComponent;
    requires LinearAccelerationComponent;
    requires AccelerationComponent;

    provides SystemSpi with MovementSystemProvider;
}