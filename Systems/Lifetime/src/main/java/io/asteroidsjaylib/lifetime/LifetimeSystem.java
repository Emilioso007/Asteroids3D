package io.asteroidsjaylib.lifetime;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.lifetime.LifetimeComponent;
import io.asteroidsjaylib.common.util.ITimeProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LifetimeSystem extends IteratingSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ITimeProvider timeProvider;

    @Override
    public void start(IWorld world) {
        this.setPriority(0);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        LifetimeComponent lifetimeComponent = entity.getComponent(LifetimeComponent.class);
        if (timeProvider.getTime()-lifetimeComponent.startTime >= lifetimeComponent.lifetime){
            entity.setToBeRemoved(true);
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(LifetimeComponent.class);
    }
}
