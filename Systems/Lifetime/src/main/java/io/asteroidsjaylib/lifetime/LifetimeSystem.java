package io.asteroidsjaylib.lifetime;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.lifetime.LifetimeComponent;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class LifetimeSystem extends IteratingSystem {
    @Override
    public void start(IWorld world) {
        this.setPriority(0);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        LifetimeComponent lifetimeComponent = entity.getComponent(LifetimeComponent.class);
        if (Duration.between(lifetimeComponent.startTime, Instant.now()).compareTo(lifetimeComponent.lifetime)>=0){
            entity.setToBeRemoved(true);
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(LifetimeComponent.class);
    }
}
