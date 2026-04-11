package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Quaternion;

public class RotationComponent extends BaseComponent {
    public Quaternion quaternion;

    public RotationComponent(){
        this.quaternion = new Quaternion();
    }

    public RotationComponent(Quaternion quaternion){
        this.quaternion = quaternion;
    }
}
