import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;

module Asteroid {
    uses io.asteroidsjaylib.common.coin.CoinSPI;
    requires Common;
    requires CommonAsteroid;
    requires CommonPhysics;
    requires CommonSpawn;
    requires CommonRender;
    requires jaylib;
    requires CommonOutOfBounds;
    requires CommonCollision;
    requires CommonCoin;

    provides EntitySpi with io.asteroidsjaylib.asteroid.AsteroidProvider;
    provides BaseSystem with io.asteroidsjaylib.asteroid.AsteroidCollisionResponseSystem;
}
