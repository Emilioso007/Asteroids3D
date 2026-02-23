package io.asteroidsfx.outofboundssystem;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.linearvelocitycomponent.LinearVelocityComponent;
import io.asteroidsfx.outofboundscomponent.OutOfBoundsComponent;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.velocitycomponent.VelocityComponent;

import java.util.List;

public class OutOfBoundsSystem extends System {

    int minX, maxX, minY, maxY;

    public OutOfBoundsSystem(int minX, int maxX, int minY, int maxY){
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    // overloaded constructor that implies 0,0
    public OutOfBoundsSystem(int maxX, int maxY){
        this(0, maxX, 0, maxY);
    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(PositionComponent.class, OutOfBoundsComponent.class);
    }

    @Override
    public void tick(float dt, List<Entity> entities) {

        for (Entity entity : entities){

            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            OutOfBoundsComponent outOfBoundsComponent = entity.getComponent(OutOfBoundsComponent.class);

            double leftEdge = positionComponent.x + outOfBoundsComponent.leftExtent; // x + negativeExtent
            double rightEdge = positionComponent.x + outOfBoundsComponent.rightExtent; // x + positiveExtent
            double topEdge = positionComponent.y + outOfBoundsComponent.topExtent; // y + negativeExtent
            double bottomEdge = positionComponent.y + outOfBoundsComponent.bottomExtent; // y + positiveExtent

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
                    if(exitLeft) positionComponent.x = maxX - outOfBoundsComponent.leftExtent;
                    if(exitRight) positionComponent.x = minX - outOfBoundsComponent.rightExtent;
                    if(exitTop) positionComponent.y = maxY - outOfBoundsComponent.topExtent;
                    if(exitBottom) positionComponent.y = minY - outOfBoundsComponent.bottomExtent;
                    break;

                case BOUNCE:

                    // 2D movement
                    VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
                    if(velocityComponent != null){
                        if(hitLeft) {
                            positionComponent.x = minX - outOfBoundsComponent.leftExtent;
                            velocityComponent.dx *= -1;
                        }
                        if(hitRight) {
                            positionComponent.x = maxX - outOfBoundsComponent.rightExtent;
                            velocityComponent.dx *= -1;
                        }
                        if(hitTop) {
                            positionComponent.y = minY - outOfBoundsComponent.topExtent;
                            velocityComponent.dy *= -1;
                        }
                        if(hitBottom) {
                            positionComponent.y = maxY - outOfBoundsComponent.bottomExtent;
                            velocityComponent.dy *= -1;
                        }
                    }

                    // Linear movement
                    AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
                    LinearVelocityComponent linearVelocityComponent = entity.getComponent(LinearVelocityComponent.class);
                    if(angleComponent != null && linearVelocityComponent != null){
                        if(hitLeft || hitRight){
                            angleComponent.angle = Math.PI - angleComponent.angle;
                        }
                        if(hitTop || hitBottom){
                            angleComponent.angle = -angleComponent.angle;
                        }
                    }

                    break;

                case REMOVE:
                    entity.toBeRemoved |= (exitLeft || exitRight || exitTop || exitBottom);
                    break;

            }
        }
    }
}