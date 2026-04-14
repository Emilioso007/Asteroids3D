import io.asteroidsjaylib.background.BackgroundProvider;
import io.asteroidsjaylib.common.ecs.EntitySpi;

module Background {
    requires Common;
    requires CommonRender;
    requires jaylib;
    requires CommonPhysics3D;

    provides EntitySpi with BackgroundProvider;
}