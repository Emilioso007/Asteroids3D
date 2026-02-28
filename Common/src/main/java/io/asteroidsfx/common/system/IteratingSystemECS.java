package io.asteroidsfx.common.system;

import io.asteroidsfx.common.Entity;

import java.util.List;

public abstract class IteratingSystemECS extends SystemECS {
    @Override
    public void update(List<Entity> entities, double deltaTime){
        for(Entity entity : entities){
            processEntity(entity, deltaTime);
        }
    }
    public abstract void processEntity(Entity entity, double deltaTime);
}
