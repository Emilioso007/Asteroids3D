package io.asteroidsjaylib.common.event.input.mouse;

import io.asteroidsjaylib.common.util.Vector2D;

public class MousePositionEvent extends MouseEvent {

    public MousePositionEvent(Vector2D screenPosition, Vector2D worldPosition) {
        super(-1, screenPosition, worldPosition);
    }
}
