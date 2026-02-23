package io.asteroidsfx.renderingsystem;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class RenderingSystem extends System{
    private final GraphicsContext gc;

    public RenderingSystem(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(RenderComponent.class);
    }

    @Override
    public void tick(float dt, List<Entity> entities) {
        for(Entity entity : entities){
            RenderComponent renderComponent = entity.getComponent(RenderComponent.class);

            // If entity has position and/or rotation, use translation/rotation to draw
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            AngleComponent angleComponent = entity.getComponent(AngleComponent.class);

            gc.save();

            if(positionComponent != null){
                gc.translate(positionComponent.x, positionComponent.y);
            }
            if(angleComponent != null){
                gc.rotate(Math.toDegrees(angleComponent.angle));
            }

            renderComponent.polygon.display(gc);

            gc.restore();
        }
    }
}
