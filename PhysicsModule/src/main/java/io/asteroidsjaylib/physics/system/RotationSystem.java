package io.asteroidsjaylib.physics.system;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.physics.component.AngleComponent;
import io.asteroidsjaylib.physics.component.RotationComponent;

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
    public void processEntity(World world, BaseEntity entity, double deltaTime) {
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
        RotationComponent rotationComponent = entity.getComponent(RotationComponent.class);

        angleComponent.angle += rotationComponent.dAngle * deltaTime;
    }
}
