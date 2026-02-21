package io.asteroidsfx.renderingsystem;

import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
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
            RenderComponent component = entity.getComponent(RenderComponent.class);
            if (component == null) {
                continue;
            }
            component.polygon.display(gc);
        }
    }
}
