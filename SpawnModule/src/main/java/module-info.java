import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.spawn.SpawnSystemProvider;

module Spawn {
    requires Common;
    requires TimerComponent;
    exports io.asteroidsfx.spawn;
    provides SystemSpi with SpawnSystemProvider;
}