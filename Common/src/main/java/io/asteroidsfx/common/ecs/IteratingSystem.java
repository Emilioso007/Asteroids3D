package io.asteroidsfx.common.ecs;

import io.asteroidsfx.common.World;

import java.util.List;

public abstract class IteratingSystem extends BaseSystem {
    @Override
    public void update(World world, List<BaseEntity> entities, double deltaTime){
        if(!running) return;

        for(BaseEntity entity : entities){
            processEntity(world, entity, deltaTime);
        }
    }
    public abstract void processEntity(World world, BaseEntity entity, double deltaTime);
}
