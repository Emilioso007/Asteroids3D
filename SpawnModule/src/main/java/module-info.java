import io.asteroidsfx.common.ecs.BaseSystem;

module Spawn {
    requires Common;
    exports io.asteroidsfx.spawn;
    provides BaseSystem with io.asteroidsfx.spawn.SpawnSystem;
}