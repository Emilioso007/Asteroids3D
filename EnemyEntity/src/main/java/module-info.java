import io.asteroidsfx.common.ecs.EntitySpi;
import io.asteroidsfx.common.ecs.BaseSystem;
import io.asteroidsfx.enemyentity.EnemyEntityProvider;
import io.asteroidsfx.enemyentity.EnemySystem;

module EnemyEntity {
    requires AngleComponent;
    requires Common;
    requires javafx.graphics;
    requires PositionComponent;
    requires RenderComponent;
    requires VelocityComponent;
    requires PlayerEntity;
    requires OutOfBounds;
    requires Collision;
    requires BulletEntity;
    requires Spawn;

    provides EntitySpi with EnemyEntityProvider;
    provides BaseSystem with EnemySystem;
}