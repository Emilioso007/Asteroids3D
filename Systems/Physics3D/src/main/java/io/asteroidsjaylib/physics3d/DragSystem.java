package io.asteroidsjaylib.physics3d;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.Drag;
import io.asteroidsjaylib.common.physics3d.Velocity;

import java.util.List;

public class DragSystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.priority(21);
    }

    @Override
    public void update(IWorld world, BaseEntity entity, float deltaTime) {

        Velocity velocity = entity.get(Velocity.class);
        Drag drag = entity.get(Drag.class);

        assert velocity != null;
        assert drag != null;

        float dragFactor = (float) Math.pow(drag.value(), deltaTime);
        velocity.vector().multiply(dragFactor);

        if(velocity.vector().magnitudeSquared()<0.01){
            velocity.vector().multiply(0);
        }

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(Velocity.class, Drag.class);
    }

}
