package io.asteroidsfx.renderingsystem;

import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;

public class RenderingSystem extends System{
    private GraphicsContext gc;

    public RenderingSystem(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void tick(float dt, HashSet<Entity> entities) {
        if (gc == null) {
            return;
        }
        for(Entity entity : entities){
            RenderComponent renderComponent = entity.getComponent(RenderComponent.class);
            if (renderComponent == null) {
                continue;
            }
            // If entity has position, use translation to draw at position
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            if(positionComponent != null){
                gc.save();
                gc.translate(positionComponent.x, positionComponent.y);
                renderComponent.polygon.display(gc);
                gc.restore();
            } else {
                renderComponent.polygon.display(gc);
            }
        }
    }
}
