import io.asteroidsfx.common.ecs.BaseSystem;
import io.asteroidsfx.lifetime.LifetimeSystem;

module Lifetime {
    requires Common;
    exports io.asteroidsfx.lifetime;

    provides BaseSystem with LifetimeSystem;
}