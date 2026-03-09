import io.asteroidsjaylib.bullet.BulletCollisionResponseSystem;
import io.asteroidsjaylib.bullet.BulletProvider;
import io.asteroidsjaylib.bulletcommon.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Bullet {
    requires Common;
    requires OwnershipCommon;
    requires CollisionCommon;
    requires BulletCommon;

    requires jaylib;
    requires PhysicsCommon;
    requires RenderCommon;
    requires OutOfBoundsCommon;
    requires LifetimeCommon;
    requires CoinCommon;

    provides BaseSystem with BulletCollisionResponseSystem;
    provides BulletSPI with BulletProvider;
}