import io.asteroidsfx.common.ecs.BaseSystem;
import io.asteroidsfx.common.ecs.EntitySpi;

module Core {
    uses EntitySpi;
    uses BaseSystem;
    requires javafx.graphics;
    requires Common;
    exports io.asteroidsfx;
}