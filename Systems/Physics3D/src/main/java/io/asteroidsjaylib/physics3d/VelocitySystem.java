package io.asteroidsjaylib.physics3d;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class VelocitySystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(22);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        Vector3D position = entity.getComponent(PositionComponent.class).pos;
        Vector3D velocity = entity.getComponent(VelocityComponent.class).vel;
        position.add(velocity.x * deltaTime, velocity.y * deltaTime, velocity.z * deltaTime);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, VelocityComponent.class);
    }

}
