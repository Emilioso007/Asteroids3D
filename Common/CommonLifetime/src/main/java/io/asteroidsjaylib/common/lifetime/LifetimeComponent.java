package io.asteroidsjaylib.common.lifetime;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class LifetimeComponent extends BaseComponent {

    public final float startTime;
    public final float lifetime;

    public LifetimeComponent(float startTime, float lifetime) {
        this.startTime = startTime;
        this.lifetime = lifetime;
    }
}