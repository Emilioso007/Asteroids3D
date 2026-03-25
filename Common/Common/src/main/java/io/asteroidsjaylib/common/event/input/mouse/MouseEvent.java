package io.asteroidsjaylib.common.event.input.mouse;

import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.util.Vector2D;

public abstract class MouseEvent extends BaseEvent {
    public int buttonCode;
    public Vector2D screenPosition;
    public Vector2D worldPosition;
    public MouseEvent(int buttonCode, Vector2D screenPosition, Vector2D worldPosition){
        this.buttonCode = buttonCode;
        this.screenPosition = screenPosition;
        this.worldPosition = worldPosition;
    }

}
