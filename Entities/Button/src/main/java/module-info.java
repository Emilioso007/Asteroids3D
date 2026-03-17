import io.asteroidsjaylib.button.ButtonProvider;
import io.asteroidsjaylib.common.button.ButtonSPI;

module Button {
    requires Common;
    requires CommonPhysics;
    requires CommonRender;
    requires jaylib;
    requires CommonButton;
    requires CommonCollision;

    provides ButtonSPI with ButtonProvider;
}