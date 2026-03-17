package io.asteroidsjaylib.common.event.input;

import io.asteroidsjaylib.common.event.BaseEvent;

public class KeyReleasedEvent extends BaseEvent {
    public final int keyCode;

    public KeyReleasedEvent(int keyCode) {
        this.keyCode = keyCode;
    }
}
