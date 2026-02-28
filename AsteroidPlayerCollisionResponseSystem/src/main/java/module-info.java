import io.asteroidsfx.common.system.SystemECS;

module AsteroidPlayerCollisionResponseSystem {
    requires AsteroidEntity;
    requires Collision;
    requires Common;
    requires PlayerEntity;

    provides SystemECS with io.asteroidsfx.asteroidplayercollisionresponsesystem.AsteroidPlayerCollisionResponseSystem;
}