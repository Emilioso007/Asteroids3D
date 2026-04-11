package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Vector3D;

public class VelocityComponent extends BaseComponent {
    public Vector3D vel;

    public VelocityComponent(){
        this.vel = new Vector3D();
    }
    public VelocityComponent(Vector3D vel){
        this.vel = vel;
    }
}
