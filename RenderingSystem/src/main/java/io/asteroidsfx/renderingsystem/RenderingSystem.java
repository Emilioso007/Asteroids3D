package io.asteroidsfx.renderingsystem;

import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.IteratingSystem;
import io.asteroidsfx.physics.component.AngleComponent;
import io.asteroidsfx.physics.component.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class RenderingSystem extends IteratingSystem {
    public GraphicsContext graphicsContext;

    @Override
    public void start(World world) {
        this.setPriority(100);
        this.graphicsContext = world.graphicsContext;
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(RenderComponent.class);
    }

    @Override
    public void processEntity(BaseEntity entity, double deltaTime) {
        RenderComponent renderComponent = entity.getComponent(RenderComponent.class);

        // If entity has position and/or rotation, use translation/rotation to draw
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);

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
    }
}
