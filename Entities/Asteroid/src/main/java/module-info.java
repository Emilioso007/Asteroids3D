import io.asteroidsjaylib.asteroid.AsteroidCollisionResponseSystem;
import io.asteroidsjaylib.asteroid.AsteroidProvider;
import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;

module Asteroid {
    uses io.asteroidsjaylib.common.coin.CoinSPI;
    uses io.asteroidsjaylib.common.asteroid.AsteroidSPI;
    requires Common;
    requires CommonAsteroid;
    requires CommonSpawn;
    requires CommonRender;
    requires CommonCollision;
    requires CommonCoin;
    requires CommonPhysics3D;

    provides AsteroidSPI with AsteroidProvider;
    provides EventSubscriberSPI with AsteroidCollisionResponseSystem;
}
