package io.asteroidsfx.ownership;

import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;

public class OwnershipComponent extends BaseComponent {
    public BaseEntity owner;
}