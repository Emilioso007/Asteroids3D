package io.asteroidsfx.physics.system;

import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.IteratingSystem;
import io.asteroidsfx.physics.component.AccelerationComponent;
import io.asteroidsfx.physics.component.VelocityComponent;

import java.util.List;

public class AccelerationSystem extends IteratingSystem {
    @Override
    public void processEntity(BaseEntity entity, double deltaTime) {
        VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
        AccelerationComponent accelerationComponent = entity.getComponent(AccelerationComponent.class);
        velocityComponent.vel.add(accelerationComponent.acc.copy().mult(deltaTime));
        accelerationComponent.acc.mult(0);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(VelocityComponent.class, AccelerationComponent.class);
    }

    @Override
    public void start(World world) {
        this.setPriority(20);
    }
}
