package io.asteroidsjaylib.physics3d;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Velocity;

import java.util.List;

public class VelocitySystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.priority(22);
    }

    @Override
    public void update(IWorld world, BaseEntity entity, float deltaTime) {

        Position position = entity.get(Position.class);
        Velocity velocity = entity.get(Velocity.class);

        assert position != null;
        assert velocity != null;

        position.vector().addScaled(velocity.vector(), deltaTime);

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(Position.class, Velocity.class);
    }

}
