package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.World;

import java.util.List;

public abstract class IteratingSystem extends BaseSystem {
    @Override
    public void update(World world, List<BaseEntity> entities, float deltaTime){
        for(BaseEntity entity : entities){
            processEntity(world, entity, deltaTime);
        }
    }
    public abstract void processEntity(World world, BaseEntity entity, float deltaTime);
}
