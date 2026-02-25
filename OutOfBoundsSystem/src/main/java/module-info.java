import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.outofboundssystem.OutOfBoundsSystemProvider;

module OutOfBoundsSystem {
    requires AngleComponent;
    requires Common;
    requires LinearVelocityComponent;
    requires OutOfBoundsComponent;
    requires PositionComponent;
    requires VelocityComponent;

    provides SystemSpi with OutOfBoundsSystemProvider;
}