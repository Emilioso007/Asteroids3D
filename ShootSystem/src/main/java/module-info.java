import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.shootsystem.ShootSystemProvider;

module ShootSystem {
    requires Common;
    requires PositionComponent;
    requires AngleComponent;
    requires ShootComponent;
    requires BulletEntity;

    provides SystemSpi with ShootSystemProvider;
}