import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;

module Core {
    uses EntitySpi;
    uses BaseSystem;
    requires Common;
    requires jaylib;
    exports io.asteroidsjaylib;
}