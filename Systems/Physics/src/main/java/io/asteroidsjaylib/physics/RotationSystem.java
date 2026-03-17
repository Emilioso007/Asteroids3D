package io.asteroidsjaylib.physics;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics.AngleComponent;
import io.asteroidsjaylib.common.physics.RotationComponent;

import java.util.List;

public class RotationSystem extends IteratingSystem {

    @Override
    public void start(World world) {
        this.setPriority(30);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(AngleComponent.class, RotationComponent.class);
    }

    @Override
    public void processEntity(World world, BaseEntity entity, float deltaTime) {
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class).orElseThrow();
        RotationComponent rotationComponent = entity.getComponent(RotationComponent.class).orElseThrow();

        angleComponent.angle += rotationComponent.dAngle * deltaTime;
    }
}
