import io.asteroidsfx.common.ecs.BaseSystem;

module MovementSystem {
    requires Common;
    requires PositionComponent;
    requires VelocityComponent;
    requires DragComponent;
    requires AccelerationComponent;

    provides BaseSystem with io.asteroidsfx.movementsystem.MovementSystem;
}