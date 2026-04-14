package io.asteroidsjaylib.outofbounds;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics2d.PositionComponent;
import io.asteroidsjaylib.common.physics2d.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.outofbounds.OutOfBoundsComponent;

import java.util.List;

public class OutOfBoundsSystem extends IteratingSystem {

    int minX, maxX, minY, maxY;

    @Override
    public void start(IWorld world) {
        this.setPriority(25);
        this.running = false;
        this.minX = 0;
        this.maxX = world.getWidth();
        this.minY = 0;
        this.maxY = world.getHeight();
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, OutOfBoundsComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        OutOfBoundsComponent outOfBoundsComponent = entity.getComponent(OutOfBoundsComponent.class);

        float leftEdge = positionComponent.pos.x + outOfBoundsComponent.leftExtent;
        float rightEdge = positionComponent.pos.x + outOfBoundsComponent.rightExtent;
        float topEdge = positionComponent.pos.y + outOfBoundsComponent.topExtent;
        float bottomEdge = positionComponent.pos.y + outOfBoundsComponent.bottomExtent;

        // For WRAP and REMOVE: check if completely outside
        boolean exitLeft = rightEdge < minX;
        boolean exitRight = leftEdge > maxX;
        boolean exitTop = bottomEdge < minY;
        boolean exitBottom = topEdge > maxY;

        // For BOUNCE: check if touching or crossing the boundary
        boolean hitLeft = leftEdge <= minX;
        boolean hitRight = rightEdge >= maxX;
        boolean hitTop = topEdge <= minY;
        boolean hitBottom = bottomEdge >= maxY;

        switch (outOfBoundsComponent.boundsAction){

            case WRAP:
                if(exitLeft) positionComponent.pos.x = (maxX - outOfBoundsComponent.leftExtent);
                if(exitRight) positionComponent.pos.x = (minX - outOfBoundsComponent.rightExtent);
                if(exitTop) positionComponent.pos.y = (maxY - outOfBoundsComponent.topExtent);
                if(exitBottom) positionComponent.pos.y = (minY - outOfBoundsComponent.bottomExtent);
                break;

            case BOUNCE:

                // 2D movement
                Vector2D vel = entity.getComponent(VelocityComponent.class).vel;
                if (hitLeft) {
                    positionComponent.pos.x = (minX - outOfBoundsComponent.leftExtent);
                    vel.x = (vel.x * -1);
                }
                if(hitRight) {
                    positionComponent.pos.x = (maxX - outOfBoundsComponent.rightExtent);
                    vel.x = (vel.x * -1);
                }
                if(hitTop) {
                    positionComponent.pos.y = (minY - outOfBoundsComponent.topExtent);
                    vel.y = (vel.y * -1);
                }
                if(hitBottom) {
                    positionComponent.pos.y = (maxY - outOfBoundsComponent.bottomExtent);
                    vel.y = (vel.y * -1);
                }

                break;

            case REMOVE:
                entity.setToBeRemoved(entity.isToBeRemoved() | (exitLeft || exitRight || exitTop || exitBottom));
                break;

        }
    }
}