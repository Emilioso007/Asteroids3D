package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.IWorld;

import java.util.List;

public abstract non-sealed class IntervalIteratingSystem extends BaseSystem {
    public double interval;
    public double accumulator;

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime){
        accumulator += deltaTime;
        if(accumulator >= interval){
            accumulator = 0;
            for (BaseEntity entity : entities){
                updateInterval(world, entity, deltaTime);
            }
        }
    }

    public abstract void updateInterval(IWorld world, BaseEntity entity, double deltaTime);
}
