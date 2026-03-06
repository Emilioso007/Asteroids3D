package io.asteroidsjaylib.render;


import com.raylib.Raylib;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.physics.component.AngleComponent;
import io.asteroidsjaylib.physics.component.PositionComponent;

import java.util.List;

import static com.raylib.Raylib.*;

public class RenderSystem extends IteratingSystem {

    @Override
    public void start(World world) {
        this.setPriority(100);
    }

    @Override
    public void processEntity(World world, BaseEntity entity, double deltaTime) {
        RenderComponent renderComponent = entity.getComponent(RenderComponent.class);

        Raylib.Vector2 position = new Raylib.Vector2();
        float angle = 0;

        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        if(positionComponent != null) position = positionComponent.pos.toRaylibVector2();

        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
        if (angleComponent != null) angle = (float) Math.toDegrees(angleComponent.angle);

        int w = world.getWidth();
        int h = world.getHeight();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                rlPushMatrix();
                rlTranslatef(i * w, j * h, 0);
                renderComponent.draw(position, angle);
                rlPopMatrix();
            }
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(RenderComponent.class);
    }
}
