import io.asteroidsfx.common.EntitySpi;
import io.asteroidsfx.common.system.SystemECS;
import io.asteroidsfx.enemyentity.EnemyEntityProvider;
import io.asteroidsfx.enemyentity.EnemySystem;

module EnemyEntity {
    requires AngleComponent;
    requires Common;
    requires javafx.graphics;
    requires PositionComponent;
    requires RenderComponent;
    requires VelocityComponent;
    requires InputComponent;
    requires PlayerEntity;
    requires OutOfBounds;
    requires Collision;
    requires BulletEntity;
    requires Spawn;
    requires TimerComponent;

    provides EntitySpi with EnemyEntityProvider;
    provides SystemECS with EnemySystem;
}