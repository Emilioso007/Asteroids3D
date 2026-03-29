package io.asteroidsjaylib.render;


import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics.AngleComponent;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.render.RenderComponent;
import io.asteroidsjaylib.common.render.RenderTag;

import java.util.List;

import static com.raylib.Raylib.*;

public class RenderSystem extends BulkSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(100);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(RenderTag.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        // Order entities based on their zIndex in ascending order, meaning higher values gets drawn last
        entities.sort((a, b) -> {
            RenderTag ra = a.getComponent(RenderTag.class).orElseThrow();
            RenderTag rb = b.getComponent(RenderTag.class).orElseThrow();
            return Integer.compare(ra.getzIndex(), rb.getzIndex());
        });

        int w = world.getWidth();
        int h = world.getHeight();

        Camera2D camera = world.getCamera();
        float viewMinX = camera.target().x() - camera.offset().x();
        float viewMaxX = camera.target().x() + (world.getScreenWidth() - camera.offset().x());
        float viewMinY = camera.target().y() - camera.offset().y();
        float viewMaxY = camera.target().y() + (world.getScreenHeight() - camera.offset().y());

        for (BaseEntity entity : entities) {

            RenderTag renderTag = entity.getComponent(RenderTag.class).orElseThrow();
            Vector2D position = entity.getComponent(PositionComponent.class).map(positionComponent -> positionComponent.pos).orElse(Vector2D.ZERO.copy());
            float angle = entity.getComponent(AngleComponent.class).map(angleComponent -> angleComponent.angle).orElse((float) 0);

            if(renderTag.isAbsolutePosition()){
                for (RenderComponent component : renderTag.getRenderComponents()){
                    component.draw(position, angle);
                }
                continue;
            }

            float margin = entity.getComponent(CircleColliderComponent.class)
                    .map(circleColliderComponent -> circleColliderComponent.radius * 2)
                    .orElse(Float.POSITIVE_INFINITY);

            for (RenderComponent component : renderTag.getRenderComponents()){

                // Apply camera offset
                BeginMode2D(world.getCamera());

                rlTranslatef(world.getCameraShake().x(), world.getCameraShake().y(), 0);

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {

                        float drawX = position.x() + (i*w);
                        float drawY = position.y() + (j*h);

                        if (drawX + margin < viewMinX ||
                            drawX - margin > viewMaxX ||
                            drawY + margin < viewMinY ||
                            drawY - margin > viewMaxY) {
                            continue;
                        }

                        rlPushMatrix();
                        rlTranslatef(i * w, j * h, 0);

                        component.draw(position, angle);

                        rlPopMatrix();
                    }
                }

                EndMode2D();
            }
        }
    }
}
