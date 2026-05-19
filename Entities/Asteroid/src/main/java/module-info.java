import io.asteroidsjaylib.asteroid.AsteroidCollisionResponseSystem;
import io.asteroidsjaylib.asteroid.AsteroidProvider;
import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.crystal.CrystalSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Asteroid {
    requires Common;
    requires CommonAsteroid;
    requires CommonCollision;
    requires CommonCrystal;
    requires CommonEnemy;
    requires CommonLifetime;
    requires CommonPhysics3D;
    requires CommonRender;
    requires CommonSpawn;
    requires spring.beans;
    requires spring.context;

    opens io.asteroidsjaylib.asteroid to spring.core, spring.context, spring.beans;

    uses CrystalSPI;
    uses AsteroidSPI;

    provides AsteroidSPI with AsteroidProvider;
    provides BaseSystem with AsteroidCollisionResponseSystem;
}
