import io.asteroidsfx.asteroidplayercollisionresponsesystem.AsteroidPlayerCollisionResponseSystemProvider;
import io.asteroidsfx.common.SystemSpi;

module AsteroidPlayerCollisionResponseSystem {
    requires AsteroidEntity;
    requires CollisionSystem;
    requires Common;
    requires PlayerEntity;

    provides SystemSpi with AsteroidPlayerCollisionResponseSystemProvider;
}