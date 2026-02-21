package io.asteroidsfx.movementsystem;

import io.asteroidsfx.accelerationcomponent.AccelerationComponent;
import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.dragcomponent.DragComponent;
import io.asteroidsfx.linearaccelerationcomponent.LinearAccelerationComponent;
import io.asteroidsfx.linearvelocitycomponent.LinearVelocityComponent;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.velocitycomponent.VelocityComponent;

import java.util.HashSet;

public class MovementSystem extends System{
    @Override
    public void tick(float dt, HashSet<Entity> entities) {
        for(Entity entity : entities){
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);

            // Apply velocity
            VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
            if (positionComponent != null && velocityComponent != null) {
                positionComponent.x += velocityComponent.dx * dt;
                positionComponent.y += velocityComponent.dy * dt;
            }
            LinearVelocityComponent linearVelocityComponent = entity.getComponent(LinearVelocityComponent.class);
            AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
            if(positionComponent != null && linearVelocityComponent != null && angleComponent != null){
                positionComponent.x += Math.cos(angleComponent.angle)*linearVelocityComponent.velocity;
                positionComponent.y += Math.sin(angleComponent.angle)*linearVelocityComponent.velocity;
            }

            //Apply acceleration
            AccelerationComponent accelerationComponent = entity.getComponent(AccelerationComponent.class);
            if(velocityComponent != null && accelerationComponent != null){
                velocityComponent.dx += accelerationComponent.accelerationX;
                velocityComponent.dy += accelerationComponent.accelerationY;
                accelerationComponent.accelerationX = 0;
                accelerationComponent.accelerationY = 0;
            }
            LinearAccelerationComponent linearAccelerationComponent = entity.getComponent(LinearAccelerationComponent.class);
            if(linearVelocityComponent != null && linearAccelerationComponent != null){
                linearVelocityComponent.velocity += linearAccelerationComponent.acceleration;
                linearAccelerationComponent.acceleration = 0;
            }

            //Apply drag
            DragComponent dragComponent = entity.getComponent(DragComponent.class);
            if(velocityComponent != null && dragComponent != null){
                velocityComponent.dx *= (1 - dragComponent.drag * dt);
                velocityComponent.dy *= (1 - dragComponent.drag * dt);
            }
            if(linearVelocityComponent != null && dragComponent != null){
                linearVelocityComponent.velocity *= (1 - dragComponent.drag * dt);
            }
        }
    }
}
