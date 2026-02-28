package io.asteroidsfx.movementsystem;

import io.asteroidsfx.accelerationcomponent.AccelerationComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.system.IteratingSystemECS;
import io.asteroidsfx.common.system.SystemECS;
import io.asteroidsfx.dragcomponent.DragComponent;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.velocitycomponent.VelocityComponent;

import java.util.List;

public class MovementSystem extends IteratingSystemECS {

    @Override
    public void start(World world) {

    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(PositionComponent.class, VelocityComponent.class);
    }

    @Override
    public void processEntity(Entity entity, double deltaTime) {
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);

        AccelerationComponent accelerationComponent = entity.getComponent(AccelerationComponent.class);
        if (accelerationComponent != null){
            velocityComponent.vel.add(accelerationComponent.acc.copy().mult(deltaTime));
            accelerationComponent.acc.mult(0);
        }

        DragComponent dragComponent = entity.getComponent(DragComponent.class);
        if(dragComponent != null){
            velocityComponent.vel.mult(Math.max(0.0f, 1.0f - (dragComponent.drag * deltaTime)));
        }

        positionComponent.pos.add(velocityComponent.vel.copy().mult(deltaTime));
    }
}
