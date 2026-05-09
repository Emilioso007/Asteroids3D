package io.asteroidsjaylib.physics3d;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.Acceleration;
import io.asteroidsjaylib.common.physics3d.Velocity;

import java.util.List;

public class AccelerationSystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.priority(20);
    }

    @Override
    public void update(IWorld world, BaseEntity entity, float deltaTime) {

        Velocity velocity = entity.get(Velocity.class);
        Acceleration acceleration = entity.get(Acceleration.class);

        assert velocity != null;
        assert acceleration != null;

        velocity.vector().addScaled(acceleration.vector(), deltaTime);

        acceleration.vector(0, 0, 0);

        velocity.vector().limit(velocity.terminal());

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(Velocity.class, Acceleration.class);
    }

}
