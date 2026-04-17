import io.asteroidsjaylib.bullet.BulletCollisionResponseSystem;
import io.asteroidsjaylib.bullet.BulletGlowSystem;
import io.asteroidsjaylib.bullet.BulletProvider;
import io.asteroidsjaylib.bullet.BulletSeekingSystem;
import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;

module Bullet {
    requires Common;

    requires CommonRender;
    requires CommonCollision;
    requires CommonBullet;
    requires CommonOwnership;
    requires CommonCrystal;
    requires CommonLifetime;
    requires CommonPhysics3D;
    requires CommonPlayer;
    requires CommonAsteroid;
    requires CommonEnemy;

    provides BulletSPI with BulletProvider;
    provides EventSubscriberSPI with BulletCollisionResponseSystem;
    provides BaseSystem with BulletGlowSystem, BulletSeekingSystem;
}