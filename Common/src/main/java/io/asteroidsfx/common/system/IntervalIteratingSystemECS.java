package io.asteroidsfx.common.system;

import io.asteroidsfx.common.Entity;

import java.util.List;

public abstract class IntervalIteratingSystemECS extends SystemECS {
    private double interval;
    private double accumulator;

    public IntervalIteratingSystemECS(double interval){
        this(interval, 0);
    }
    public IntervalIteratingSystemECS(double interval, int priority){
        super(priority);
        this.interval = interval;
        this.accumulator = 0;
    }

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
