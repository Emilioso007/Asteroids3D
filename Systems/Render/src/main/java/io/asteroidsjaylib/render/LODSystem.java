package io.asteroidsjaylib.render;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.render.Base3DShape;
import io.asteroidsjaylib.common.render.Render3D;

import java.util.List;

public class LODSystem extends IteratingSystem {

    @Override
    public void start(IWorld world) {
        this.priority(99);
    }

    @Override
    public void update(IWorld world, BaseEntity entity, float deltaTime) {

        Position position = entity.get(Position.class);
        Render3D render3D = entity.get(Render3D.class);
        assert position != null;
        assert render3D != null;

        float distance = position.vector().magnitude();

        for(Base3DShape shape : render3D.getActiveShapes()){

            shape.currentLodLevel = Math.min(shape.lodCount - 1, (int)(distance / (5000.0f / shape.lodCount)));

        }

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(Render3D.class, Position.class);
    }
}
