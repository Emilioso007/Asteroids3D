import io.asteroidsfx.asteroidbulletcollisionresponsesystem.AsteroidBulletCollisionResponseSystemProvider;
import io.asteroidsfx.common.SystemSpi;

module AsteroidBulletCollisionResponseSystem {
    requires AsteroidEntity;
    requires BulletEntity;
    requires CollisionSystem;
    requires Common;

    provides SystemSpi with AsteroidBulletCollisionResponseSystemProvider;
}