package io.asteroidsjaylib.common.event.input.mouse;

import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.util.Vector2D;

public class MousePositionEvent extends BaseEvent {
    public Vector2D position;

    public MousePositionEvent(Vector2D position) {
        this.position = position;
    }
}
