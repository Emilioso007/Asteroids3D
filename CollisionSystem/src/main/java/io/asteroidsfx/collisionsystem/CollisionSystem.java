package io.asteroidsfx.collisionsystem;

import io.asteroidsfx.circlecollidercomponent.CircleColliderComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.common.World;
import io.asteroidsfx.positioncomponent.PositionComponent;

import java.util.List;

public class CollisionSystem extends System {

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(PositionComponent.class, CircleColliderComponent.class);
    }

    @Override
    public void tick(float dt, List<Entity> entities) {

        for(Entity collider : entities){

            PositionComponent colliderPosition = collider.getComponent(PositionComponent.class);
            CircleColliderComponent colliderCircle = collider.getComponent(CircleColliderComponent.class);

            for(Entity target : entities){

                if(collider == target) continue;

                PositionComponent targetPosition = target.getComponent(PositionComponent.class);
                CircleColliderComponent targetCircle = target.getComponent(CircleColliderComponent.class);

                double distanceBetweenCenters = Math.sqrt(Math.pow(targetPosition.x-colliderPosition.x, 2) + Math.pow(targetPosition.y-colliderPosition.y, 2));
                double radiusSum = colliderCircle.radius + targetCircle.radius;
                if(distanceBetweenCenters <= radiusSum){
                    World.getInstance().getEventBus().publish(new CollisionEvent(collider, target));
                }
            }
        }
    }
}