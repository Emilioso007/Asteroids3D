package io.asteroidsfx.common.ecs;

import io.asteroidsfx.common.World;

import java.util.List;

public abstract class ResponseSystem extends BaseSystem {

    @Override
    public final List<Class<? extends BaseComponent>> getSignature() {
        return List.of();
    }

    @Override
    public final void update(World world, List<BaseEntity> entities, double deltaTime){}
}
