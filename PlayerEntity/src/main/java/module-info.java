import io.asteroidsfx.common.ecs.BaseSystem;
import io.asteroidsfx.common.ecs.EntitySpi;
import io.asteroidsfx.playerentity.PlayerEntityProvider;
import io.asteroidsfx.playerentity.PlayerSystem;

module PlayerEntity {
    exports io.asteroidsfx.playerentity;
    requires Common;
    requires javafx.graphics;
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
    requires RotationComponent;

    provides EntitySpi with PlayerEntityProvider;
    provides BaseSystem with PlayerSystem;
}