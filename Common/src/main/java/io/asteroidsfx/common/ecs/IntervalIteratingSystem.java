package io.asteroidsfx.common.ecs;

import io.asteroidsfx.common.World;

import java.util.List;

public abstract class IntervalIteratingSystem extends BaseSystem {
    public double interval;
    public double accumulator;

    @Override
    public void update(World world, List<BaseEntity> entities, double deltaTime){
        if(!running) return;

        accumulator += deltaTime;
        if(accumulator >= interval){
            accumulator = 0;
            for (BaseEntity entity : entities){
                updateInterval(world, entity, deltaTime);
            }
        }
    }

    public abstract void updateInterval(World world, BaseEntity entity, double deltaTime);
}
