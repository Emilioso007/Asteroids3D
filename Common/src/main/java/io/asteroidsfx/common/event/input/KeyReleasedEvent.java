package io.asteroidsfx.common.event.input;

import io.asteroidsfx.common.event.BaseEvent;
import javafx.scene.input.KeyCode;

public class KeyReleasedEvent extends BaseEvent {
    public KeyCode keyCode;

    public KeyReleasedEvent(KeyCode keyCode) {
        this.keyCode = keyCode;
    }
}
