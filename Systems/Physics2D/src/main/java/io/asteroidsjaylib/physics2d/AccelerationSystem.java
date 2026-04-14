package io.asteroidsjaylib.physics2d;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics2d.AccelerationComponent;
import io.asteroidsjaylib.common.physics2d.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.List;

public class AccelerationSystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(20);
        this.running = false;
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        Vector2D velocity = entity.getComponent(VelocityComponent.class).vel;
        Vector2D acceleration = entity.getComponent(AccelerationComponent.class).acc;
        velocity.add(acceleration.x*deltaTime, acceleration.y*deltaTime);
        acceleration.mult(0);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(VelocityComponent.class, AccelerationComponent.class);
    }

}
