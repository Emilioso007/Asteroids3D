import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.player.*;

module Player {
    uses BulletSPI;
    requires Common;
    requires jaylib;
    requires CommonPlayer;
    requires CommonRender;
    requires CommonSpawn;
    requires CommonCollision;
    requires CommonCrystal;
    requires CommonOwnership;
    requires CommonBullet;
    requires CommonPhysics3D;

    provides EntitySpi with PlayerEntityProvider;
    provides BaseSystem with PlayerMovementSystem, PlayerShootingSystem, PlayerThreadmillSystem;
    provides EventSubscriberSPI with PlayerCollisionResponseSystem;
}