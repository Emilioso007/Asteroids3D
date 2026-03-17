package io.asteroidsjaylib.common.event.input;

import io.asteroidsjaylib.common.event.BaseEvent;

public class KeyDownEvent extends BaseEvent {
    public final int keyCode;

    public KeyDownEvent(int keyCode) {
        this.keyCode = keyCode;
    }
}
