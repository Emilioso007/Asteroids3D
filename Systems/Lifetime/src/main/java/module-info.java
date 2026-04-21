import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.lifetime.LifetimeSystem;

module Lifetime {
    requires Common;
    requires CommonLifetime;
    requires spring.beans;

    opens io.asteroidsjaylib.lifetime to spring.core, spring.beans, spring.context;

    provides BaseSystem with LifetimeSystem;
}