package io.asteroidsjaylib.physics2d;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics2d.DragComponent;
import io.asteroidsjaylib.common.physics2d.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.List;

public class DragSystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(21);
        this.running = false;
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        Vector2D velocity = entity.getComponent(VelocityComponent.class).vel;
        float drag = entity.getComponent(DragComponent.class).drag;

        if (drag == 0) {
            velocity.mult(0);
        } else {
            velocity.mult((float) Math.pow(drag, deltaTime));
        }

        if(velocity.mag()<0.01){
            velocity.mult(0);
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(VelocityComponent.class, DragComponent.class);
    }

}
