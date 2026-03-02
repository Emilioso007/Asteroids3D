package io.asteroidsfx.common.event.input;

import io.asteroidsfx.common.event.BaseEvent;
import javafx.scene.input.KeyCode;

public class KeyPressedEvent extends BaseEvent {
    public KeyCode keyCode;

    public KeyPressedEvent(KeyCode keyCode) {
        this.keyCode = keyCode;
    }
}
