import io.asteroidsfx.common.EntitySpi;
import io.asteroidsfx.playerentity.PlayerEntityProvider;

module PlayerEntity {
    exports io.asteroidsfx.playerentity;
    requires Common;
    requires javafx.graphics;
    requires InputComponent;
    requires PositionComponent;
    requires AngleComponent;
    requires RenderComponent;
    requires DragComponent;
    requires VelocityComponent;
    requires AccelerationComponent;
    requires OutOfBounds;
    requires Collision;
    requires BulletEntity;
    requires Spawn;
    requires TimerComponent;

    provides EntitySpi with PlayerEntityProvider;
}