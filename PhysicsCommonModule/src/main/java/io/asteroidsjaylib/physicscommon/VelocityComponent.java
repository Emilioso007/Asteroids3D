package io.asteroidsjaylib.physicscommon;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Vector;

public class VelocityComponent extends BaseComponent {

    public Vector vel;

    public VelocityComponent(){
        this.vel = new Vector();
    }
    public VelocityComponent(Vector vel){
        this.vel = vel;
    }

}
