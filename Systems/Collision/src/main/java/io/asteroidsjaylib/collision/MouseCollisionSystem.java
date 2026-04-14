package io.asteroidsjaylib.collision;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.button.ClickedEvent;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.event.input.mouse.MousePressedEvent;
import io.asteroidsjaylib.common.physics2d.PositionComponent;
import io.asteroidsjaylib.common.render.RenderTag;

import java.util.List;

public class MouseCollisionSystem extends ResponseSystem {
    @Override
    public void start(IWorld world) {
        world.getEventBus().subscribe(MousePressedEvent.class, this::handleMousePressed);
    }

    private void handleMousePressed(IWorld world, MousePressedEvent mousePressedEvent) {

        List<BaseEntity> entities = world.getEntitiesWith(PositionComponent.class, CircleColliderComponent.class);

        if (entities.isEmpty()) return;

        for (BaseEntity entity : entities){

            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            CircleColliderComponent circleColliderComponent = entity.getComponent(CircleColliderComponent.class);

            // Check if entity uses absolute screen position
            boolean isAbsolutePosition = false;
            var renderTag = entity.getComponent(RenderTag.class);
            if (renderTag.isAbsolutePosition()) {
                isAbsolutePosition = true;
            }

            double dx, dy;

            if (isAbsolutePosition) {
                // For absolute position entities: use screen coordinates directly
                dx = mousePressedEvent.screenPosition.x - positionComponent.pos.x;
                dy = mousePressedEvent.screenPosition.y - positionComponent.pos.y;
            } else {
                // For world entities: use converted world coordinates
                dx = mousePressedEvent.worldPosition.x - positionComponent.pos.x;
                dy = mousePressedEvent.worldPosition.y - positionComponent.pos.y;
            }

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= circleColliderComponent.radius){
                world.getEventBus().publish(world, new ClickedEvent(entity));
            }

        }

    }
}
