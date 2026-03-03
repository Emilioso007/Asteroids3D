package io.asteroidsfx.collision;

import io.asteroidsfx.common.*;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.BaseSystem;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.physics.component.PositionComponent;

import java.util.List;

public class CollisionSystem extends BaseSystem {

    @Override
    public void start(World world) {

    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, CircleColliderComponent.class);
    }

    @Override
    public void update(World world, List<BaseEntity> entities, double deltaTime) {
        for(int i = 0; i < entities.size() - 1; i++){

            BaseEntity collider = entities.get(i);

            PositionComponent colliderPosition = collider.getComponent(PositionComponent.class);
            CircleColliderComponent colliderCircle = collider.getComponent(CircleColliderComponent.class);

            for(int j = i + 1; j < entities.size(); j++){

                BaseEntity target = entities.get(j);

                PositionComponent targetPosition = target.getComponent(PositionComponent.class);
                CircleColliderComponent targetCircle = target.getComponent(CircleColliderComponent.class);

                double distanceBetweenCenters = Vector.dist(colliderPosition.pos, targetPosition.pos);
                double radiusSum = colliderCircle.radius + targetCircle.radius;
                if(distanceBetweenCenters <= radiusSum){
                    world.getEventBus().publish(world, new CollisionEvent(collider, target));
                }
            }
        }
    }
}