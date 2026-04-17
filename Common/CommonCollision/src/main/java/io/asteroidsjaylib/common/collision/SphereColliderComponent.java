package io.asteroidsjaylib.common.collision;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class SphereColliderComponent extends BaseComponent {
    public float radius;

    public SphereColliderComponent(float radius) {
        this.radius = radius;
    }
}
