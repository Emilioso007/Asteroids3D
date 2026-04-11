import io.asteroidsjaylib.bullet.BulletCollisionResponseSystem;
import io.asteroidsjaylib.bullet.BulletProvider;
import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Bullet {
    requires Common;

    requires jaylib;
    requires CommonPhysics2D;
    requires CommonRender;
    requires CommonCollision;
    requires CommonBullet;
    requires CommonOwnership;
    requires CommonCoin;
    requires CommonLifetime;
    requires CommonOutOfBounds;
    requires CommonSound;

    provides BaseSystem with BulletCollisionResponseSystem;
    provides BulletSPI with BulletProvider;
}