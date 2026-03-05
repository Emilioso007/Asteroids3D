package io.asteroidsjaylib.common.event.input;

import io.asteroidsjaylib.common.event.BaseEvent;

public class KeyPressedEvent extends BaseEvent {
    public int keyCode;

    public KeyPressedEvent(int keyCode) {
        this.keyCode = keyCode;
    }
}
