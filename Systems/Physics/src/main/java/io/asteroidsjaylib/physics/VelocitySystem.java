package io.asteroidsjaylib.physics;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.physics.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.List;

public class VelocitySystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(22);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        Vector2D position = entity.getComponent(PositionComponent.class).orElseThrow().pos;
        Vector2D velocity = entity.getComponent(VelocityComponent.class).orElseThrow().vel;
        position.add(velocity.x()*deltaTime, velocity.y()*deltaTime);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, VelocityComponent.class);
    }

}
