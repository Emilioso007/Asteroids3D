package io.asteroidsjaylib.common.event.input.mouse;

import io.asteroidsjaylib.common.util.Vector2D;

public class MouseUpEvent extends MouseEvent{
    public MouseUpEvent(int buttonCode, Vector2D screenPosition, Vector2D worldPosition) {
        super(buttonCode, screenPosition, worldPosition);
    }
}
