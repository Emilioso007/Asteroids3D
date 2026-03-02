package io.asteroidsfx.physics.system;

import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.IteratingSystem;
import io.asteroidsfx.physics.component.DragComponent;
import io.asteroidsfx.physics.component.VelocityComponent;

import java.util.List;

public class DragSystem extends IteratingSystem {
    @Override
    public void processEntity(BaseEntity entity, double deltaTime) {
        VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
        DragComponent dragComponent = entity.getComponent(DragComponent.class);

        if (dragComponent.drag == 0) {
            velocityComponent.vel.mult(0);
        } else {
            velocityComponent.vel.mult(Math.pow(dragComponent.drag, deltaTime));
        }

        if(velocityComponent.vel.mag()<0.01){
            velocityComponent.vel.mult(0);
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(VelocityComponent.class, DragComponent.class);
    }

    @Override
    public void start(World world) {
        this.setPriority(21);
    }
}
