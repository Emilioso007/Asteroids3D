import io.asteroidsjaylib.asteroid.AsteroidCollisionResponseSystem;
import io.asteroidsjaylib.asteroid.AsteroidProvider;
import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Asteroid {
    uses io.asteroidsjaylib.common.coin.CoinSPI;
    uses io.asteroidsjaylib.common.asteroid.AsteroidSPI;
    requires Common;
    requires CommonAsteroid;
    requires CommonPhysics2D;
    requires CommonSpawn;
    requires CommonRender;
    requires jaylib;
    requires CommonOutOfBounds;
    requires CommonCollision;
    requires CommonCoin;
    requires CommonPhysics3D;

    provides AsteroidSPI with AsteroidProvider;
    provides BaseSystem with AsteroidCollisionResponseSystem;
}
