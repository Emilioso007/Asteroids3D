package io.asteroidsjaylib.render;


import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.physicscommon.AngleComponent;
import io.asteroidsjaylib.physicscommon.PositionComponent;
import io.asteroidsjaylib.rendercommon.RenderComponent;
import io.asteroidsjaylib.rendercommon.RenderTag;

import java.util.List;

import static com.raylib.Raylib.*;

public class RenderSystem extends BulkSystem {

    @Override
    public void start(World world) {
        this.setPriority(100);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(RenderTag.class);
    }

    @Override
    public void update(World world, List<BaseEntity> entities, double deltaTime) {

        // Order entities based on their zIndex in ascending order, meaning higher values gets drawn last
        entities.sort((a, b) -> {
            RenderTag ra = a.getComponent(RenderTag.class);
            RenderTag rb = b.getComponent(RenderTag.class);
            return Integer.compare(ra.getzIndex(), rb.getzIndex());
        });

        int w = world.getWidth();
        int h = world.getHeight();

        for (BaseEntity entity : entities) {

            RenderTag renderTag = entity.getComponent(RenderTag.class);
            if(renderTag.isAbsolutePosition()){
                for (RenderComponent component : renderTag.getRenderComponents()){
                    FinalPlacement result = getFinalPlacement(entity, component);

                    result.renderComponent().draw(result.position(), result.angle());
                }
                continue;
            }

            for (RenderComponent component : renderTag.getRenderComponents()){

                // Apply camera offset
                rlPushMatrix();
                rlTranslatef(
                        (float)(-world.cameraLocation.x + world.screenWidth  / 2.0),
                        (float)(-world.cameraLocation.y + world.screenHeight / 2.0),
                        0
                );

                FinalPlacement result = getFinalPlacement(entity, component);

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        rlPushMatrix();
                        rlTranslatef(i * w, j * h, 0);

                        rlPushMatrix();
                        rlTranslatef((float) component.xoffset, (float) component.yoffset, 0);

                        component.draw(result.position(), result.angle);

                        rlPopMatrix();

                        rlPopMatrix();
                    }
                }

                rlPopMatrix();
            }
        }
    }

    private static FinalPlacement getFinalPlacement(BaseEntity entity, RenderComponent renderComponent) {
        Vector2 position = new Vector2();
        float angle = 0;

        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        if (positionComponent != null) position = positionComponent.pos.toRaylibVector2();

        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
        if (angleComponent != null) angle = (float) Math.toDegrees(angleComponent.angle);
        return new FinalPlacement(renderComponent, position, angle);
    }

    private record FinalPlacement(RenderComponent renderComponent, Vector2 position, float angle) {
    }
}
