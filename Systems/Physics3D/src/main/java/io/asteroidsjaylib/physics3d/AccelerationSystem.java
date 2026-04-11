package io.asteroidsjaylib.physics3d;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.AccelerationComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class AccelerationSystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(20);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        Vector3D velocity = entity.getComponent(VelocityComponent.class).orElseThrow().vel;
        Vector3D acceleration = entity.getComponent(AccelerationComponent.class).orElseThrow().acc;
        velocity.add(acceleration.copy().mult(deltaTime));
        acceleration.mult(0);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(VelocityComponent.class, AccelerationComponent.class);
    }

}
