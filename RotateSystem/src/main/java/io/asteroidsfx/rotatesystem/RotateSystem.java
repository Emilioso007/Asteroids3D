package io.asteroidsfx.rotatesystem;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.rotationcomponent.RotationComponent;

import java.util.List;

public class RotateSystem extends System {

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(AngleComponent.class, RotationComponent.class);
    }

    @Override
    public void tick(float dt, List<Entity> entities) {
        for(Entity entity : entities){
            AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
            RotationComponent rotationComponent = entity.getComponent(RotationComponent.class);

            angleComponent.angle += rotationComponent.dAngle * dt;
        }
    }
}
