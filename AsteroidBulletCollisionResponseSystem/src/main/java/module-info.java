import io.asteroidsfx.common.system.SystemECS;

module AsteroidBulletCollisionResponseSystem {
    requires AsteroidEntity;
    requires BulletEntity;
    requires Collision;
    requires Common;
    requires PositionComponent;
    requires VelocityComponent;

    provides SystemECS with io.asteroidsfx.asteroidbulletcollisionresponsesystem.AsteroidBulletCollisionResponseSystem;
}