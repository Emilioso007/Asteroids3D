package io.asteroidsfx.renderingsystem;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.system.IteratingSystemECS;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class RenderingSystem extends IteratingSystemECS {
    public GraphicsContext graphicsContext;

    @Override
    public void start(World world) {
        this.graphicsContext = world.graphicsContext;
    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(RenderComponent.class);
    }

    @Override
    public void processEntity(Entity entity, double deltaTime) {
        RenderComponent renderComponent = entity.getComponent(RenderComponent.class);

        // If entity has position and/or rotation, use translation/rotation to draw
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);

        graphicsContext.save();

        if(positionComponent != null){
            graphicsContext.translate(positionComponent.pos.x, positionComponent.pos.y);
        }
        if(angleComponent != null){
            graphicsContext.rotate(Math.toDegrees(angleComponent.angle));
        }

        renderComponent.polygon.display(graphicsContext);

        graphicsContext.restore();
    }
}
