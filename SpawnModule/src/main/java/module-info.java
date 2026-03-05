import io.asteroidsjaylib.common.ecs.BaseSystem;

module Spawn {
    requires Common;
    exports io.asteroidsjaylib.spawn;
    provides BaseSystem with io.asteroidsjaylib.spawn.SpawnSystem;
}