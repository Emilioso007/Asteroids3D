package io.asteroidsjaylib.collision;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.collision.SphereCollider;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.util.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

public class Collision3DSystem extends BulkSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void start(IWorld world) {
        this.priority(70);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        for (int i = 0; i < entities.size(); i++){

            BaseEntity entityA = entities.get(i);
            Position positionA = entityA.get(Position.class);
            SphereCollider sphereColliderA = entityA.get(SphereCollider.class);
            assert positionA != null;
            assert sphereColliderA != null;

            for(int j = i+1; j < entities.size(); j++){

                BaseEntity entityB = entities.get(j);
                Position positionB = entityB.get(Position.class);
                SphereCollider sphereColliderB = entityB.get(SphereCollider.class);
                assert positionB != null;
                assert sphereColliderB != null;

                float distance = Vector3D.distanceSquared(positionA.vector(), positionB.vector());

                float radiiSum = sphereColliderA.radius() + sphereColliderB.radius();

                if (distance < (radiiSum*radiiSum)){
                    eventPublisher.publishEvent(new CollisionEvent(entityA, entityB));
                }

            }

        }

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(Position.class, SphereCollider.class);
    }

}
