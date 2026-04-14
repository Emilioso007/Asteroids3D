import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.enemy.EnemySPI;
import io.asteroidsjaylib.enemy.EnemyCollisionResponseSystem;
import io.asteroidsjaylib.enemy.EnemyProvider;
import io.asteroidsjaylib.enemy.EnemySystem;

module Enemy {
    uses BulletSPI;
    requires Common;

    requires CommonRender;
    requires CommonPlayer;
    requires CommonScore;
    requires CommonBullet;
    requires CommonEnemy;
    requires CommonCollision;
    requires CommonOwnership;
    requires CommonCoin;
    requires CommonPhysics2D;
    requires CommonPhysics3D;

    provides EnemySPI with EnemyProvider;
    provides BaseSystem with EnemySystem, EnemyCollisionResponseSystem;
}