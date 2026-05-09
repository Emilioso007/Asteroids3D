package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;

/// The percentage of velocity that remains after 1 second (0.0 = instant stop, 1.0 = no friction).
public final class Drag extends BaseComponent {
    private float value;

    public Drag() {
        this.value = 0;
    }

    public float value() {
        return this.value;
    }

    public Drag value(float value) {
        this.value = value;
        return this;
    }
}