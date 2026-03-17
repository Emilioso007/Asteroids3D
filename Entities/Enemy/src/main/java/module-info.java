import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.enemy.EnemyCollisionResponseSystem;
import io.asteroidsjaylib.enemy.EnemyEntityProvider;
import io.asteroidsjaylib.enemy.EnemySystem;

module Enemy {
    uses BulletSPI;
    requires Common;

    requires jaylib;
    requires CommonPhysics;
    requires CommonRender;
    requires CommonPlayer;
    requires CommonSpawn;
    requires CommonScore;
    requires CommonBullet;
    requires CommonEnemy;
    requires CommonCollision;
    requires CommonOwnership;
    requires CommonOutOfBounds;

    provides EntitySpi with EnemyEntityProvider;
    provides BaseSystem with EnemySystem, EnemyCollisionResponseSystem;
}