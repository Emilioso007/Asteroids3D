import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.player.*;

module Player {
    requires Common;
    requires CommonBullet;
    requires CommonCollision;
    requires CommonCrystal;
    requires CommonOwnership;
    requires CommonPhysics3D;
    requires CommonPlayer;
    requires CommonRender;
    requires CommonSpawn;
    requires io.github.electronstudio.jaylib.ffm;
    requires spring.context;
    requires spring.beans;

    opens io.asteroidsjaylib.player to spring.core, spring.context, spring.beans;

    uses BulletSPI;

    provides EntitySpi with PlayerEntityProvider;
    provides BaseSystem with PlayerMovementSystem, PlayerShootingSystem, PlayerThreadmillSystem, PlayerCollisionResponseSystem;
}

