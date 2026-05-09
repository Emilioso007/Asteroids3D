package io.asteroidsjaylib.common.lifetime;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public final class Lifetime extends BaseComponent {

    private final float start;
    private final float duration;

    public Lifetime(float start, float duration) {
        this.start = start;
        this.duration = duration;
    }

    public float start() {
        return start;
    }

    public float duration() {
        return duration;
    }

    public float remaining(float currentTime) {
        return duration - (currentTime - start);
    }
}