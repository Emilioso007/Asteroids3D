package io.asteroidsfx.collision;

import io.asteroidsfx.common.*;
import io.asteroidsfx.common.System;
import io.asteroidsfx.positioncomponent.PositionComponent;

import java.util.List;

public class CollisionSystem extends System {

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(PositionComponent.class, CircleColliderComponent.class);
    }

    @Override
    public void tick(float dt, List<Entity> entities) {

        for(int i = 0; i < entities.size() - 1; i++){

            Entity collider = entities.get(i);

            PositionComponent colliderPosition = collider.getComponent(PositionComponent.class);
            CircleColliderComponent colliderCircle = collider.getComponent(CircleColliderComponent.class);

            for(int j = i + 1; j < entities.size(); j++){

                Entity target = entities.get(j);

                PositionComponent targetPosition = target.getComponent(PositionComponent.class);
                CircleColliderComponent targetCircle = target.getComponent(CircleColliderComponent.class);

                float distanceBetweenCenters = Vector.dist(colliderPosition.pos, targetPosition.pos);
                float radiusSum = colliderCircle.radius + targetCircle.radius;
                if(distanceBetweenCenters <= radiusSum){
                    World.getInstance().getEventBus().publish(new CollisionEvent(collider, target));
                }
            }
        }
    }
}