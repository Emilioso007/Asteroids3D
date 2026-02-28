import io.asteroidsfx.common.system.SystemECS;

module Spawn {
    requires Common;
    requires TimerComponent;
    exports io.asteroidsfx.spawn;
    provides SystemECS with io.asteroidsfx.spawn.SpawnSystem;
}