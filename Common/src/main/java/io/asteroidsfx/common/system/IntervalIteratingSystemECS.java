package io.asteroidsfx.common.system;

import io.asteroidsfx.common.Entity;

import java.util.List;

public abstract class IntervalIteratingSystemECS extends SystemECS {
    public double interval;
    private double accumulator;

    @Override
    public void update(List<Entity> entities, double deltaTime){
        accumulator += deltaTime;
        if(accumulator >= interval){
            accumulator = 0;
            for (Entity entity : entities){
                updateInterval(entity, deltaTime);
            }
        }
    }

    public abstract void updateInterval(Entity entity, double deltaTime);
}
