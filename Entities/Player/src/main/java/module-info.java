import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.player.PlayerCollisionResponseSystem;
import io.asteroidsjaylib.player.PlayerEntityProvider;
import io.asteroidsjaylib.player.PlayerMovementSystem;
import io.asteroidsjaylib.player.PlayerShootingSystem;

module Player {
    uses BulletSPI;
    requires Common;
    requires jaylib;
    requires CommonPlayer;
    requires CommonRender;
    requires CommonSpawn;
    requires CommonCollision;
    requires CommonCoin;
    requires CommonOwnership;
    requires CommonBullet;
    requires CommonPhysics3D;

    provides EntitySpi with PlayerEntityProvider;
    provides BaseSystem with PlayerMovementSystem;
    provides EventSubscriberSPI with PlayerCollisionResponseSystem, PlayerShootingSystem;
}