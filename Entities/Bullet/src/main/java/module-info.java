import io.asteroidsjaylib.bullet.BulletCollisionResponseSystem;
import io.asteroidsjaylib.bullet.BulletProvider;
import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;

module Bullet {
    requires Common;

    requires jaylib;
    requires CommonRender;
    requires CommonCollision;
    requires CommonBullet;
    requires CommonOwnership;
    requires CommonCoin;
    requires CommonLifetime;
    requires CommonPhysics3D;

    provides BulletSPI with BulletProvider;
    provides EventSubscriberSPI with BulletCollisionResponseSystem;
}