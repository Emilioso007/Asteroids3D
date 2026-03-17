package io.asteroidsjaylib.common.event.input.mouse;

import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.util.Vector2D;

public abstract class MouseEvent extends BaseEvent {
    public int buttonCode;
    public Vector2D position;
    public MouseEvent(int buttonCode, Vector2D position){
        this.buttonCode = buttonCode;
        this.position = position;
    }

}
