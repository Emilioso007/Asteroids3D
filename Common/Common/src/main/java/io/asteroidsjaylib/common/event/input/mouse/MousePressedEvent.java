package io.asteroidsjaylib.common.event.input.mouse;

import io.asteroidsjaylib.common.util.Vector2D;

public class MousePressedEvent extends MouseEvent{
    public MousePressedEvent(int buttonCode, Vector2D screenPosition, Vector2D worldPosition) {
        super(buttonCode, screenPosition, worldPosition);
    }
}
