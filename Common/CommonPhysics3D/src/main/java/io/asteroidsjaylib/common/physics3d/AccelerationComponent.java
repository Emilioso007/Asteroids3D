package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Vector3D;

public class AccelerationComponent extends BaseComponent {
    public Vector3D acc;

    public AccelerationComponent(){
        this.acc = new Vector3D();
    }

}
