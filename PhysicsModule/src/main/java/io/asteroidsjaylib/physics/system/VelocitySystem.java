package io.asteroidsjaylib.physics.system;

import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.physics.component.PositionComponent;
import io.asteroidsjaylib.physics.component.VelocityComponent;

import java.util.List;

public class VelocitySystem extends IteratingSystem {

    @Override
    public void start(World world) {
        this.setPriority(22);
    }

    @Override
    public void processEntity(World world, BaseEntity entity, double deltaTime) {
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
        positionComponent.pos.add(velocityComponent.vel.copy().mult(deltaTime));
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, VelocityComponent.class);
    }

}
