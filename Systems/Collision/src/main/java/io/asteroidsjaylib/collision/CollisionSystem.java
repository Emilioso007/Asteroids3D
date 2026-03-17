package io.asteroidsjaylib.collision;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.List;

public class CollisionSystem extends BaseSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(70);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, CircleColliderComponent.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {
        for(int i = 0; i < entities.size() - 1; i++){

            BaseEntity collider = entities.get(i);

            Vector2D colliderPosition = collider.getComponent(PositionComponent.class).orElseThrow().pos;
            float colliderCircle = collider.getComponent(CircleColliderComponent.class).orElseThrow().radius;

            for(int j = i + 1; j < entities.size(); j++){

                BaseEntity target = entities.get(j);

                Vector2D targetPosition = target.getComponent(PositionComponent.class).orElseThrow().pos;
                float targetCircle = target.getComponent(CircleColliderComponent.class).orElseThrow().radius;

                double distanceBetweenCenters = Vector2D.dist(colliderPosition, targetPosition);
                double radiusSum = colliderCircle + targetCircle;
                if(distanceBetweenCenters <= radiusSum){
                    world.getEventBus().publish(world, new CollisionEvent(collider, target));
                }
            }
        }
    }
}