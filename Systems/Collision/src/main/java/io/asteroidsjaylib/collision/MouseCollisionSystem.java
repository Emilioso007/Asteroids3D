package io.asteroidsjaylib.collision;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.button.ClickedEvent;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.common.event.EventSubscription;
import io.asteroidsjaylib.common.event.input.mouse.MousePressedEvent;
import io.asteroidsjaylib.common.physics2d.PositionComponent;
import io.asteroidsjaylib.common.render.RenderTag;

import java.util.List;

public class MouseCollisionSystem implements EventSubscriberSPI {
    @Override
    public List<EventSubscription<? extends BaseEvent>> getEventSubscriptions() {
        return List.of(new EventSubscription<>(MousePressedEvent.class, this::handleMousePressed));
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

            double distance = getDistance(mousePressedEvent, isAbsolutePosition, positionComponent);

            if (distance <= circleColliderComponent.radius){
                world.getEventBus().publish(world, new ClickedEvent(entity));
            }

        }

    }

    private static double getDistance(MousePressedEvent mousePressedEvent, boolean isAbsolutePosition, PositionComponent positionComponent) {
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

        return Math.sqrt(dx * dx + dy * dy);
    }
}
