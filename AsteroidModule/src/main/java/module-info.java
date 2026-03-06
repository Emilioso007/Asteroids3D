import io.asteroidsjaylib.asteroid.AsteroidCollisionResponseSystem;
import io.asteroidsjaylib.asteroid.AsteroidProvider;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;

module Asteroid {
    requires Collision;
    requires Common;
    requires OutOfBounds;
    requires Physics;

    requires jaylib;
    requires Render;

    exports io.asteroidsjaylib.asteroid;

    provides EntitySpi with AsteroidProvider;
    provides BaseSystem with AsteroidCollisionResponseSystem;
}