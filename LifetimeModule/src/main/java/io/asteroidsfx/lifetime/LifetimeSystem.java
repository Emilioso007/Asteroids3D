package io.asteroidsfx.lifetime;

import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.IteratingSystem;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class LifetimeSystem extends IteratingSystem {
    @Override
    public void processEntity(BaseEntity entity, double deltaTime) {
        LifetimeComponent lifetimeComponent = entity.getComponent(LifetimeComponent.class);
        if (Duration.between(lifetimeComponent.startTime, Instant.now()).compareTo(lifetimeComponent.lifetime)>=0){
            entity.setToBeRemoved(true);
        }
    }

    @Override
    public void start(World world) {

    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(LifetimeComponent.class);
    }
}
