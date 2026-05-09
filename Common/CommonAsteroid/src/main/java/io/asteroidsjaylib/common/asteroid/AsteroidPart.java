package io.asteroidsjaylib.common.asteroid;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public final class AsteroidPart extends BaseComponent {
    private final Type type;

    public AsteroidPart(Type type) {
        this.type = type;
    }

    public Type type() {
        return type;
    }

    public enum Type {
        Full,
        Top,
        Bottom
    }
}
