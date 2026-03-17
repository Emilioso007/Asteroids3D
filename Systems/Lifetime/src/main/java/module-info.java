import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.lifetime.LifetimeSystem;

module Lifetime {
    requires Common;
    requires CommonLifetime;

    provides BaseSystem with LifetimeSystem;
}