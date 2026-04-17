
import io.asteroidsjaylib.crystal.CrystalCollisionResponseSystem;
import io.asteroidsjaylib.crystal.CrystalGlowSystem;
import io.asteroidsjaylib.crystal.CrystalProvider;
import io.asteroidsjaylib.common.crystal.CrystalSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;

module Crystal {
    requires Common;
    requires CommonCollision;
    requires CommonPlayer;
    requires CommonCrystal;
    requires CommonRender;
    requires jaylib;
    requires CommonPhysics3D;
    provides CrystalSPI with CrystalProvider;
    provides EventSubscriberSPI with CrystalCollisionResponseSystem;
    provides BaseSystem with CrystalGlowSystem;
}