package io.asteroidsjaylib.common.physics2d;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class AngleComponent extends BaseComponent {
    /// Angle in degrees
    public float angle;
    public AngleComponent(float angle){
        this.angle = angle;
    }
    public AngleComponent(){
        angle = 0;
    }
}