import io.asteroidsfx.common.system.SystemECS;

module RotateSystem {
    requires AngleComponent;
    requires Common;
    requires RotationComponent;

    provides SystemECS with io.asteroidsfx.rotatesystem.RotateSystem;
}