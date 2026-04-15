package io.asteroidsjaylib.common.asteroid;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class AsteroidTypeComponent extends BaseComponent {
    public AsteroidType type;

    public AsteroidTypeComponent(AsteroidType type) {
        this.type = type;
    }
}
