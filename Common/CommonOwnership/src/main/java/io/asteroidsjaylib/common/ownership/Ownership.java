package io.asteroidsjaylib.common.ownership;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;

public final class Ownership extends BaseComponent {
    private BaseEntity owner;

    public Ownership(BaseEntity owner) {
        this.owner = owner;
    }

    public BaseEntity owner() {
        return owner;
    }

    public Ownership owner(BaseEntity owner) {
        this.owner = owner;
        return this;
    }
}