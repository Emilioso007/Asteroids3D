import io.asteroidsjaylib.common.ecs.BaseSystem;

module Spawn {
    requires Common;
    requires CommonSpawn;
    provides BaseSystem with io.asteroidsjaylib.spawn.SpawnSystem;
}