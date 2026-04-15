package io.asteroidsjaylib.outofbounds;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class OutOfBoundsSystem extends IteratingSystem {

    float min, max, full;

    @Override
    public void start(IWorld world) {
        this.setPriority(25);
        world.getWorldSize();
        this.min = -world.getWorldSize()/2;
        this.max = world.getWorldSize()/2;
        this.full = world.getWorldSize();
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        Vector3D pos = entity.getComponent(PositionComponent.class).pos;

        if(pos.x < min) pos.x += full;
        if(pos.x > max) pos.x -= full;
        if(pos.y < min) pos.y += full;
        if(pos.y > max) pos.y -= full;
        if(pos.z < min) pos.z += full;
        if(pos.z > max) pos.z -= full;

    }
}