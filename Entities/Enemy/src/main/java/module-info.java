import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.enemy.EnemySPI;
import io.asteroidsjaylib.enemy.EnemyCollisionResponseSystem;
import io.asteroidsjaylib.enemy.EnemyProvider;
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
    requires CommonCoin;

    provides EnemySPI with EnemyProvider;
    provides BaseSystem with EnemySystem, EnemyCollisionResponseSystem;
}