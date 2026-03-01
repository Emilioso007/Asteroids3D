package io.asteroidsfx.physics.system;

import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.IteratingSystem;
import io.asteroidsfx.physics.component.AngleComponent;
import io.asteroidsfx.physics.component.RotationComponent;

import java.util.List;

public class RotationSystem extends IteratingSystem {

    @Override
    public void start(World world) {

    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(AngleComponent.class, RotationComponent.class);
    }

    @Override
    public void processEntity(BaseEntity entity, double deltaTime) {
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
        RotationComponent rotationComponent = entity.getComponent(RotationComponent.class);

        angleComponent.angle += rotationComponent.dAngle * deltaTime;
    }
}
