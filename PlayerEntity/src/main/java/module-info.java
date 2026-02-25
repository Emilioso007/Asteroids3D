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
    requires LinearVelocityComponent;
    requires LinearAccelerationComponent;
    requires DragComponent;
    requires CircleColliderComponent;
    requires ShootComponent;
    requires OutOfBoundsComponent;

    provides EntitySpi with PlayerEntityProvider;
}