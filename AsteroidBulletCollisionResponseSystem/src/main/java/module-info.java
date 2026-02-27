import io.asteroidsfx.asteroidbulletcollisionresponsesystem.AsteroidBulletCollisionResponseSystemProvider;
import io.asteroidsfx.common.SystemSpi;

module AsteroidBulletCollisionResponseSystem {
    requires AsteroidEntity;
    requires BulletEntity;
    requires Collision;
    requires Common;
    requires PositionComponent;
    requires VelocityComponent;

    provides SystemSpi with AsteroidBulletCollisionResponseSystemProvider;
}