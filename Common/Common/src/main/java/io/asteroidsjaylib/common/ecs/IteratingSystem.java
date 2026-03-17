package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.IWorld;

import java.util.List;

public abstract class IteratingSystem extends BaseSystem {
    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime){
        for(BaseEntity entity : entities){
            processEntity(world, entity, deltaTime);
        }
    }
    public abstract void processEntity(IWorld world, BaseEntity entity, float deltaTime);
}
