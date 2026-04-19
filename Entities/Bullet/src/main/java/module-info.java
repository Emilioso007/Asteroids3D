import io.asteroidsjaylib.bullet.BulletCollisionResponseSystem;
import io.asteroidsjaylib.bullet.BulletGlowSystem;
import io.asteroidsjaylib.bullet.BulletProvider;
import io.asteroidsjaylib.bullet.BulletSeekingSystem;
import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;

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
    requires spring.context;

    opens io.asteroidsjaylib.bullet to spring.core, spring.context, spring.beans;

    provides BulletSPI with BulletProvider;
    provides BaseSystem with BulletGlowSystem, BulletSeekingSystem, BulletCollisionResponseSystem;
}