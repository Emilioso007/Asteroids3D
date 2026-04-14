import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.spawn.SpawnSystem;
import io.asteroidsjaylib.spawn.WaveDirectorSystem;

module Spawn {
    uses io.asteroidsjaylib.common.asteroid.AsteroidSPI;
    uses io.asteroidsjaylib.common.enemy.EnemySPI;
    uses io.asteroidsjaylib.common.ecs.IGameStateProvider;
    requires Common;
    requires CommonSpawn;
    requires CommonAsteroid;
    requires CommonEnemy;
    requires CommonPlayer;
    provides BaseSystem with WaveDirectorSystem;
    provides EventSubscriberSPI with SpawnSystem;
}