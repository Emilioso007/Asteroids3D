package io.asteroidsfx.movementsystem;

import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.velocitycomponent.VelocityComponent;

import java.util.HashSet;

public class MovementSystem extends System{
    @Override
    public void tick(float dt, HashSet<Entity> entities) {
        for(Entity entity : entities){
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);

            if (positionComponent == null || velocityComponent == null) {
                continue;
            }

            positionComponent.x += velocityComponent.dx * dt;
            positionComponent.y += velocityComponent.dy * dt;

        }
    }
}
