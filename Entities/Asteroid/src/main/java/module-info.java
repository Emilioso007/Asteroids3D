import io.asteroidsjaylib.asteroid.AsteroidCollisionResponseSystem;
import io.asteroidsjaylib.asteroid.AsteroidProvider;
import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;

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

    provides AsteroidSPI with AsteroidProvider;
    provides BaseSystem with AsteroidCollisionResponseSystem;
}
