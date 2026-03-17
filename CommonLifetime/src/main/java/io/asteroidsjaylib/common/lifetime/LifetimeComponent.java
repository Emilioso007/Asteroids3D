package io.asteroidsjaylib.common.lifetime;

import io.asteroidsjaylib.common.ecs.BaseComponent;

import java.time.Duration;
import java.time.Instant;

public class LifetimeComponent extends BaseComponent {

    public final Instant startTime;
    public final Duration lifetime;

    public LifetimeComponent(Duration lifetime) {
        this.startTime = Instant.now();
        this.lifetime = lifetime;
    }
}