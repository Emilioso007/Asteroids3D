import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.enemy.EnemySPI;
import io.asteroidsjaylib.spawn.SpawnSystem;
import io.asteroidsjaylib.spawn.WaveDirectorSystem;

module Spawn {
    requires Common;
    requires CommonSpawn;
    requires CommonAsteroid;
    requires CommonEnemy;
    requires spring.context;
    requires spring.beans;

    opens io.asteroidsjaylib.spawn to spring.core, spring.context, spring.beans;

    uses AsteroidSPI;
    uses EnemySPI;

    provides BaseSystem with WaveDirectorSystem, SpawnSystem;
}