import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.rotatesystem.RotateSystemProvider;

module RotateSystem {
    requires AngleComponent;
    requires Common;
    requires RotationComponent;

    provides SystemSpi with RotateSystemProvider;
}