package io.asteroidsfx.collisionsystem;

import io.asteroidsfx.circlecollidercomponent.CircleColliderComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.positioncomponent.PositionComponent;

import java.util.ArrayList;

public class CollisionSystem<T1 extends Entity, T2 extends Entity> extends System {

    public Class<T1> collider;
    public Class<T2> target;
    public CollisionAction collisionAction;

    public CollisionSystem(Class<T1> collider, Class<T2> target, CollisionAction collisionAction){
        this.collider = collider;
        this.target = target;
        this.collisionAction = collisionAction;
    }

    @Override
    public void tick(float dt, ArrayList<Entity> entities) {

        for(int i = entities.size()-1; i >= 0; i--){

            Entity collider = entities.get(i);

            if(!this.collider.isInstance(collider)) continue;

            PositionComponent colliderPosition = collider.getComponent(PositionComponent.class);
            CircleColliderComponent colliderCircle = collider.getComponent(CircleColliderComponent.class);

            if(colliderPosition == null || colliderCircle == null) continue;

            for(int j = entities.size()-1; j >= 0; j--){

                Entity target = entities.get(j);

                if(!this.target.isInstance(target)) continue;

                PositionComponent targetPosition = target.getComponent(PositionComponent.class);
                CircleColliderComponent targetCircle = target.getComponent(CircleColliderComponent.class);

                if(targetPosition == null || targetCircle == null) continue;

                double distanceBetweenCenters = Math.sqrt(Math.pow(targetPosition.x-colliderPosition.x, 2) + Math.pow(targetPosition.y-colliderPosition.y, 2));
                double radiusSum = colliderCircle.radius + targetCircle.radius;
                if(distanceBetweenCenters <= radiusSum){
                    collisionAction.execute(collider, target);
                }

            }
        }
    }
}