import io.asteroidsfx.common.system.SystemECS;

module MovementSystem {
    requires Common;
    requires PositionComponent;
    requires VelocityComponent;
    requires AngleComponent;
    requires DragComponent;
    requires AccelerationComponent;

    provides SystemECS with io.asteroidsfx.movementsystem.MovementSystem;
}