package io.asteroidsjaylib.renderingsystem;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.physics.component.AngleComponent;
import io.asteroidsjaylib.physics.component.PositionComponent;
import io.asteroidsjaylib.rendercomponent.RenderComponent;

import static com.raylib.Raylib.*;

import java.util.List;

public class RenderingSystem extends IteratingSystem {

    @Override
    public void start(World world) {
        this.setPriority(100);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(RenderComponent.class);
    }

    @Override
    public void processEntity(World world, BaseEntity entity, double deltaTime) {
        RenderComponent renderComponent = entity.getComponent(RenderComponent.class);

        // If entity has position and/or rotation, use translation/rotation to draw
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){

                rlPushMatrix();
                rlTranslatef(world.getWidth()*i, world.getHeight()*j, 0);

                rlPushMatrix();
                rlTranslatef((float) renderComponent.shape.xOffset, (float) renderComponent.shape.yOffset, 0);

                if(positionComponent != null){
                    rlTranslatef((float) positionComponent.pos.x, (float) positionComponent.pos.y, 0);
                }

                if(angleComponent != null){
                    rlRotatef((float) Math.toDegrees(angleComponent.angle), 0, 0, 1);
                }

                renderComponent.shape.draw();

                rlPopMatrix();

                rlPopMatrix();

                /*
                graphicsContext.save();
                graphicsContext.translate(world.getWidth()*i, world.getHeight()*j);

                graphicsContext.save();

                graphicsContext.translate(renderComponent.shape.xOffset, renderComponent.shape.yOffset);

                if(positionComponent != null){
                    graphicsContext.translate(positionComponent.pos.x, positionComponent.pos.y);
                }
                if(angleComponent != null){
                    graphicsContext.rotate(Math.toDegrees(angleComponent.angle));
                }

                renderComponent.shape.draw(graphicsContext);

                graphicsContext.restore();

                graphicsContext.restore();
                 */
            }
        }
    }
}
