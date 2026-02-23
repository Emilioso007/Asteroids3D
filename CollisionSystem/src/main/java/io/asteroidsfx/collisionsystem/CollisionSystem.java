package io.asteroidsfx.collisionsystem;

import io.asteroidsfx.circlecollidercomponent.CircleColliderComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.positioncomponent.PositionComponent;

import java.util.List;

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
    public List<Class<? extends Component>> getSignature() {
        return List.of(PositionComponent.class, CircleColliderComponent.class);
    }

    @Override
    public void tick(float dt, List<Entity> entities) {

        for(Entity collider : entities){

            if(!this.collider.isInstance(collider)) continue;

            PositionComponent colliderPosition = collider.getComponent(PositionComponent.class);
            CircleColliderComponent colliderCircle = collider.getComponent(CircleColliderComponent.class);

            for(Entity target : entities){

                if(collider == target) continue;

                if(!this.target.isInstance(target)) continue;

                PositionComponent targetPosition = target.getComponent(PositionComponent.class);
                CircleColliderComponent targetCircle = target.getComponent(CircleColliderComponent.class);

                double distanceBetweenCenters = Math.sqrt(Math.pow(targetPosition.x-colliderPosition.x, 2) + Math.pow(targetPosition.y-colliderPosition.y, 2));
                double radiusSum = colliderCircle.radius + targetCircle.radius;
                if(distanceBetweenCenters <= radiusSum){
                    collisionAction.execute(collider, target);
                }

            }
        }
    }
}