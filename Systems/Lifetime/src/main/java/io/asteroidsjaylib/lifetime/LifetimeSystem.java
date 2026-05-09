package io.asteroidsjaylib.lifetime;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.lifetime.Lifetime;
import io.asteroidsjaylib.common.util.ITimeProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LifetimeSystem extends IteratingSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ITimeProvider timeProvider;

    @Override
    public void start(IWorld world) {
        this.priority(0);
    }

    @Override
    public void update(IWorld world, BaseEntity entity, float deltaTime) {

        Lifetime lifetime = entity.get(Lifetime.class);
        assert lifetime != null;

        if (lifetime.remaining(timeProvider.getTime()) <= 0){
            entity.removed(true);
        }

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(Lifetime.class);
    }
}
