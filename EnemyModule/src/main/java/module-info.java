import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.enemy.EnemyCollisionResponseSystem;
import io.asteroidsjaylib.enemy.EnemyEntityProvider;
import io.asteroidsjaylib.enemy.EnemySystem;

module Enemy {
    requires Common;
    requires RenderComponent;
    requires Player;
    requires OutOfBounds;
    requires Collision;
    requires Bullet;
    requires Spawn;
    requires Physics;
    requires Ownership;

    requires jaylib;

    provides EntitySpi with EnemyEntityProvider;
    provides BaseSystem with EnemySystem, EnemyCollisionResponseSystem;
}