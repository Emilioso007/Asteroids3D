package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.IWorld;

import java.util.List;

public abstract non-sealed class IteratingSystem extends BaseSystem {
    @Override
    public final void update(IWorld world, List<BaseEntity> entities, float deltaTime){
        for(BaseEntity entity : entities){
            update(world, entity, deltaTime);
        }
    }
    public abstract void update(IWorld world, BaseEntity entity, float deltaTime);
}
