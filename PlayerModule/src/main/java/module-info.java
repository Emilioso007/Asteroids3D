import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.player.PlayerCollisionResponseSystem;
import io.asteroidsjaylib.player.PlayerEntityProvider;
import io.asteroidsjaylib.player.PlayerMovementSystem;
import io.asteroidsjaylib.player.PlayerShootingSystem;

module Player {
    uses io.asteroidsjaylib.bulletcommon.BulletSPI;
    requires Common;
    requires OwnershipCommon;
    requires jaylib;
    requires CollisionCommon;
    requires PlayerCommon;
    requires PhysicsCommon;
    requires RenderCommon;
    requires OutOfBoundsCommon;
    requires BulletCommon;
    requires SpawnCommon;
    requires CoinCommon;

    provides EntitySpi with PlayerEntityProvider;
    provides BaseSystem with PlayerShootingSystem, PlayerMovementSystem, PlayerCollisionResponseSystem;
}