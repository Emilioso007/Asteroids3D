package io.asteroidsjaylib.collision;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class Collision3DSystem extends BulkSystem {
    @Override
    public void start(IWorld world) {
        this.setPriority(70);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, SphereColliderComponent.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        for (int i = 0; i < entities.size(); i++){

            BaseEntity entityA = entities.get(i);
            Vector3D posA = entityA.getComponent(PositionComponent.class).pos;
            float radiusA = entityA.getComponent(SphereColliderComponent.class).radius;

            for(int j = i+1; j < entities.size(); j++){

                BaseEntity entityB = entities.get(j);
                Vector3D posB = entityB.getComponent(PositionComponent.class).pos;
                float radiusB = entityB.getComponent(SphereColliderComponent.class).radius;

                float distance = posA.dist(posB);

                if (distance < (radiusA + radiusB)){
                    world.getEventBus().publish(world, new CollisionEvent(entityA, entityB));
                }

            }

        }

    }
}
