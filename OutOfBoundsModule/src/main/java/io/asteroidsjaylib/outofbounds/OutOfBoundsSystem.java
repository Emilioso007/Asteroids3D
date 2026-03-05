package io.asteroidsjaylib.outofbounds;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.physics.component.PositionComponent;
import io.asteroidsjaylib.physics.component.VelocityComponent;

import java.util.List;

public class OutOfBoundsSystem extends IteratingSystem {

    int minX, maxX, minY, maxY;

    @Override
    public void start(World world) {
        this.setPriority(25);
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
    public void processEntity(World world, BaseEntity entity, double deltaTime) {
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        OutOfBoundsComponent outOfBoundsComponent = entity.getComponent(OutOfBoundsComponent.class);

        double leftEdge = positionComponent.pos.x + outOfBoundsComponent.leftExtent; // x + negativeExtent
        double rightEdge = positionComponent.pos.x + outOfBoundsComponent.rightExtent; // x + positiveExtent
        double topEdge = positionComponent.pos.y + outOfBoundsComponent.topExtent; // y + negativeExtent
        double bottomEdge = positionComponent.pos.y + outOfBoundsComponent.bottomExtent; // y + positiveExtent

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
                if(exitLeft) positionComponent.pos.x = maxX - outOfBoundsComponent.leftExtent;
                if(exitRight) positionComponent.pos.x = minX - outOfBoundsComponent.rightExtent;
                if(exitTop) positionComponent.pos.y = maxY - outOfBoundsComponent.topExtent;
                if(exitBottom) positionComponent.pos.y = minY - outOfBoundsComponent.bottomExtent;
                break;

            case BOUNCE:

                // 2D movement
                VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
                if(velocityComponent != null){
                    if(hitLeft) {
                        positionComponent.pos.x = minX - outOfBoundsComponent.leftExtent;
                        velocityComponent.vel.x *= -1;
                    }
                    if(hitRight) {
                        positionComponent.pos.x = maxX - outOfBoundsComponent.rightExtent;
                        velocityComponent.vel.x *= -1;
                    }
                    if(hitTop) {
                        positionComponent.pos.y = minY - outOfBoundsComponent.topExtent;
                        velocityComponent.vel.y *= -1;
                    }
                    if(hitBottom) {
                        positionComponent.pos.y = maxY - outOfBoundsComponent.bottomExtent;
                        velocityComponent.vel.y *= -1;
                    }
                }

                break;

            case REMOVE:
                entity.setToBeRemoved(entity.isToBeRemoved() | (exitLeft || exitRight || exitTop || exitBottom));
                break;

        }
    }
}