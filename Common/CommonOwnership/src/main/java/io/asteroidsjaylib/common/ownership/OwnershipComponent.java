package io.asteroidsjaylib.common.ownership;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;

public class OwnershipComponent extends BaseComponent {
    public BaseEntity owner;

    public OwnershipComponent(BaseEntity owner) {
        this.owner = owner;
    }
}