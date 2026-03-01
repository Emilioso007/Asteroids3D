package io.asteroidsfx.physics.system;

import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.IteratingSystem;
import io.asteroidsfx.physics.component.PositionComponent;
import io.asteroidsfx.physics.component.VelocityComponent;

import java.util.List;

public class VelocitySystem extends IteratingSystem {
    @Override
    public void processEntity(BaseEntity entity, double deltaTime) {
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
        positionComponent.pos.add(velocityComponent.vel.copy().mult(deltaTime));
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, VelocityComponent.class);
    }

    @Override
    public void start(World world) {
        this.priority = 22;
    }
}
