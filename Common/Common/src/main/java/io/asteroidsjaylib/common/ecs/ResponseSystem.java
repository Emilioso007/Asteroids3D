package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.IWorld;

import java.util.List;

public abstract class ResponseSystem extends BaseSystem {

    @Override
    public final List<Class<? extends BaseComponent>> getSignature() {
        return List.of();
    }

    @Override
    public final void update(IWorld world, List<BaseEntity> entities, float deltaTime){}
}
