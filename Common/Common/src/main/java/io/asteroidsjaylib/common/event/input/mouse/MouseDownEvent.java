package io.asteroidsjaylib.common.event.input.mouse;

import io.asteroidsjaylib.common.util.Vector2D;

public class MouseDownEvent extends MouseEvent{
    public MouseDownEvent(int buttonCode, Vector2D screenPosition, Vector2D worldPosition) {
        super(buttonCode, screenPosition, worldPosition);
    }
}
