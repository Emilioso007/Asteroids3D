package io.asteroidsjaylib.common.physics;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Vector2D;

public class VelocityComponent extends BaseComponent {

    public Vector2D vel;

    public VelocityComponent(){
        this.vel = new Vector2D();
    }
    public VelocityComponent(Vector2D vel){
        this.vel = vel;
    }

}
