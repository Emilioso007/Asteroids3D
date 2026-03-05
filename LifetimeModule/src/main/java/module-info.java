import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.lifetime.LifetimeSystem;

module Lifetime {
    requires Common;
    exports io.asteroidsjaylib.lifetime;

    provides BaseSystem with LifetimeSystem;
}