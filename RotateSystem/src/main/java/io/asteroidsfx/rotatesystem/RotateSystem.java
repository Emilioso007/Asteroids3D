package io.asteroidsfx.rotatesystem;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.system.IteratingSystemECS;
import io.asteroidsfx.rotationcomponent.RotationComponent;

import java.util.List;

public class RotateSystem extends IteratingSystemECS {


    @Override
    public void start(World world) {

    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(AngleComponent.class, RotationComponent.class);
    }

    @Override
    public void processEntity(Entity entity, double deltaTime) {
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
        RotationComponent rotationComponent = entity.getComponent(RotationComponent.class);

        angleComponent.angle += rotationComponent.dAngle * deltaTime;
    }
}
