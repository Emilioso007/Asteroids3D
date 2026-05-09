package io.asteroidsjaylib.common.collision;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public final class SphereCollider extends BaseComponent {
    private float radius;

    public SphereCollider() {
        this.radius = 0;
    }

    public float radius() {
        return radius;
    }

    public SphereCollider radius(float radius) {
        this.radius = radius;
        return this;
    }
}
