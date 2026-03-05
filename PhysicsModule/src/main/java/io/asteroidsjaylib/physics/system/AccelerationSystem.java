package io.asteroidsjaylib.physics.system;

import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.physics.component.AccelerationComponent;
import io.asteroidsjaylib.physics.component.VelocityComponent;

import java.util.List;

public class AccelerationSystem extends IteratingSystem {

    @Override
    public void start(World world) {
        this.setPriority(20);
    }

    @Override
    public void processEntity(World world, BaseEntity entity, double deltaTime) {
        VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
        AccelerationComponent accelerationComponent = entity.getComponent(AccelerationComponent.class);
        velocityComponent.vel.add(accelerationComponent.acc.copy().mult(deltaTime));
        accelerationComponent.acc.mult(0);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(VelocityComponent.class, AccelerationComponent.class);
    }

}
