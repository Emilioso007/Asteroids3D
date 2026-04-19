package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.IWorld;

import java.util.List;

public non-sealed class ResponseSystem extends BaseSystem {
    @Override
    public void start(IWorld world) {
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of();
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

    }
}
