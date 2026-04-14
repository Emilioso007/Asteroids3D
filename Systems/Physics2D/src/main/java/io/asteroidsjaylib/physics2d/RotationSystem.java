package io.asteroidsjaylib.physics2d;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics2d.AngleComponent;
import io.asteroidsjaylib.common.physics2d.RotationComponent;

import java.util.List;

public class RotationSystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(30);
        this.running = false;
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(AngleComponent.class, RotationComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
        RotationComponent rotationComponent = entity.getComponent(RotationComponent.class);

        angleComponent.angle += rotationComponent.dAngle * deltaTime;
    }
}
