import io.asteroidsfx.common.system.SystemECS;

module AsteroidBulletCollisionResponseSystem {
    requires Common;
    requires AsteroidEntity;
    requires BulletEntity;
    requires Collision;
    requires PositionComponent;
    requires VelocityComponent;

    provides SystemECS with io.asteroidsfx.asteroidbulletcollisionresponsesystem.AsteroidBulletCollisionResponseSystem;
}