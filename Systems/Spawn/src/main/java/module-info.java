import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.spawn.SpawnSystem;
import io.asteroidsjaylib.spawn.WaveDirectorSystem;

module Spawn {
    uses io.asteroidsjaylib.common.asteroid.AsteroidSPI;
    uses io.asteroidsjaylib.common.enemy.EnemySPI;
    requires Common;
    requires CommonSpawn;
    requires CommonAsteroid;
    requires CommonEnemy;
    requires spring.context;
    requires spring.beans;

    opens io.asteroidsjaylib.spawn to spring.core, spring.context, spring.beans;

    provides BaseSystem with WaveDirectorSystem, SpawnSystem;
}