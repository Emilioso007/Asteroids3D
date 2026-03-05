package io.asteroidsjaylib.physics.system;

import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.physics.component.DragComponent;
import io.asteroidsjaylib.physics.component.VelocityComponent;

import java.util.List;

public class DragSystem extends IteratingSystem {

    @Override
    public void start(World world) {
        this.setPriority(21);
    }

    @Override
    public void processEntity(World world, BaseEntity entity, double deltaTime) {
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

}
