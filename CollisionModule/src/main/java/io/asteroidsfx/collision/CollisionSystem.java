package io.asteroidsfx.collision;

import io.asteroidsfx.common.*;
import io.asteroidsfx.common.system.SystemECS;
import io.asteroidsfx.positioncomponent.PositionComponent;

import java.util.List;

public class CollisionSystem extends SystemECS {

    @Override
    public void start(World world) {

    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(PositionComponent.class, CircleColliderComponent.class);
    }

    @Override
    public void update(List<Entity> entities, double deltaTime) {
        for(int i = 0; i < entities.size() - 1; i++){

            Entity collider = entities.get(i);

            PositionComponent colliderPosition = collider.getComponent(PositionComponent.class);
            CircleColliderComponent colliderCircle = collider.getComponent(CircleColliderComponent.class);

            for(int j = i + 1; j < entities.size(); j++){

                Entity target = entities.get(j);

                PositionComponent targetPosition = target.getComponent(PositionComponent.class);
                CircleColliderComponent targetCircle = target.getComponent(CircleColliderComponent.class);

                double distanceBetweenCenters = Vector.dist(colliderPosition.pos, targetPosition.pos);
                double radiusSum = colliderCircle.radius + targetCircle.radius;
                if(distanceBetweenCenters <= radiusSum){
                    World.getInstance().getEventBus().publish(new CollisionEvent(collider, target));
                }
            }
        }
    }
}