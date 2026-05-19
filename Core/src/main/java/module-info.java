import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;

module Core {
    requires Common;
    requires io.github.electronstudio.jaylib.ffm;
    requires spring.beans;
    requires spring.context;

    opens io.asteroidsjaylib to spring.core, spring.beans, spring.context;

    uses BaseSystem;
    uses EntitySpi;
}