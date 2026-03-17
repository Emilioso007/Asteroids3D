package io.asteroidsjaylib.common.event.input.key;

import io.asteroidsjaylib.common.event.BaseEvent;

public abstract class KeyEvent extends BaseEvent {
    public int keyCode;

    public KeyEvent(int keyCode) {
        this.keyCode = keyCode;
    }
}
