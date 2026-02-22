package io.asteroidsfx.wraparoundsystem;

import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.positioncomponent.PositionComponent;

import java.util.ArrayList;

public class WraparoundSystem extends System {

    int minX, maxX, minY, maxY;

    public WraparoundSystem(int minX, int maxX, int minY, int maxY){
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    // overloaded constructor that implies 0,0
    public WraparoundSystem(int maxX, int maxY){
        this(0, maxX, 0, maxY);
    }

    @Override
    public void tick(float dt, ArrayList<Entity> entities) {
        for (Entity entity : entities){
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            WrapComponent wrapComponent = entity.getComponent(WrapComponent.class);
            if (positionComponent == null || wrapComponent == null) {
                continue;
            }

            double leftEdge = positionComponent.x + wrapComponent.leftExtent;
            double rightEdge = positionComponent.x + wrapComponent.rightExtent;
            double topEdge = positionComponent.y + wrapComponent.topExtent;
            double bottomEdge = positionComponent.y + wrapComponent.bottomExtent;

            if (wrapComponent.wrapOutside) {
                if(rightEdge < minX) positionComponent.x = maxX - wrapComponent.leftExtent;
                if(leftEdge > maxX) positionComponent.x = minX - wrapComponent.rightExtent;
                if(bottomEdge < minY) positionComponent.y = maxY - wrapComponent.topExtent;
                if(topEdge > maxY) positionComponent.y = minY - wrapComponent.bottomExtent;
            } else {
                if(leftEdge <= minX) positionComponent.x = maxX - wrapComponent.rightExtent;
                if(rightEdge >= maxX) positionComponent.x = minX - wrapComponent.leftExtent;
                if(topEdge <= minY) positionComponent.y = maxY - wrapComponent.bottomExtent;
                if(bottomEdge >= maxY) positionComponent.y = minY - wrapComponent.topExtent;
            }
        }
    }

}