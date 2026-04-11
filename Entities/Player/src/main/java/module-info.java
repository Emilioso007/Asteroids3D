import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.player.PlayerCollisionResponseSystem;
import io.asteroidsjaylib.player.PlayerEntityProvider;
import io.asteroidsjaylib.player.PlayerMovementSystem;
import io.asteroidsjaylib.player.PlayerShootingSystem;

module Player {
    uses BulletSPI;
    requires Common;
    requires jaylib;
    requires CommonPlayer;
    requires CommonPhysics2D;
    requires CommonRender;
    requires CommonSpawn;
    requires CommonCollision;
    requires CommonCoin;
    requires CommonOwnership;
    requires CommonOutOfBounds;
    requires CommonBullet;
    requires CommonSound;
    requires CommonPhysics3D;

    provides EntitySpi with PlayerEntityProvider;
    provides BaseSystem with PlayerShootingSystem, PlayerMovementSystem, PlayerCollisionResponseSystem;
}