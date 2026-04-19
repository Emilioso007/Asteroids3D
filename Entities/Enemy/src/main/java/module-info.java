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
    requires CommonBullet;
    requires CommonEnemy;
    requires CommonCollision;
    requires CommonOwnership;
    requires CommonCrystal;
    requires CommonPhysics3D;
    requires CommonSpawn;
    requires CommonAsteroid;
    requires spring.context;
    requires spring.beans;

    opens io.asteroidsjaylib.enemy to spring.core, spring.context, spring.beans;

    provides EnemySPI with EnemyProvider;
    provides BaseSystem with EnemySystem, EnemyCollisionResponseSystem;
}