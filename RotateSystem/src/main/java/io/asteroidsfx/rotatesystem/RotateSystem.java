package io.asteroidsfx.rotatesystem;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.rotationcomponent.RotationComponent;

import java.util.ArrayList;

public class RotateSystem extends System {

    @Override
    public void tick(float dt, ArrayList<Entity> entities) {
        for(Entity entity : entities){
            AngleComponent angleComponent = entity.getComponent(AngleComponent.class);
            RotationComponent rotationComponent = entity.getComponent(RotationComponent.class);

            if(angleComponent == null || rotationComponent == null){
                continue;
            }

            angleComponent.angle += rotationComponent.dAngle * dt;

        }
    }
}