
import io.asteroidsjaylib.crystal.CrystalCollisionResponseSystem;
import io.asteroidsjaylib.crystal.CrystalGlowSystem;
import io.asteroidsjaylib.crystal.CrystalProvider;
import io.asteroidsjaylib.common.crystal.CrystalSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Crystal {
    requires Common;
    requires CommonCollision;
    requires CommonPlayer;
    requires CommonCrystal;
    requires CommonRender;
    requires jaylib;
    requires CommonPhysics3D;
    requires spring.context;

    opens io.asteroidsjaylib.crystal to spring.core, spring.context, spring.beans;

    provides CrystalSPI with CrystalProvider;
    provides BaseSystem with CrystalGlowSystem, CrystalCollisionResponseSystem;
}