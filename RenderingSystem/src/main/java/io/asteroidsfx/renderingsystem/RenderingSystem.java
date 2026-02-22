package io.asteroidsfx.renderingsystem;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class RenderingSystem extends System{
    private GraphicsContext gc;

    public RenderingSystem(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void tick(float dt, ArrayList<Entity> entities) {
        if (gc == null) {
            return;
        }
        for(Entity entity : entities){
            RenderComponent renderComponent = entity.getComponent(RenderComponent.class);
            if (renderComponent == null) {
                continue;
            }

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
