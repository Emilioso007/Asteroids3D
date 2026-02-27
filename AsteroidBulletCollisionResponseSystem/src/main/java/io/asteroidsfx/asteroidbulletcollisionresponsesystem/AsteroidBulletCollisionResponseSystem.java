package io.asteroidsfx.asteroidbulletcollisionresponsesystem;

import io.asteroidsfx.asteroidentity.AsteroidEntity;
import io.asteroidsfx.asteroidentity.AsteroidSizeComponent;
import io.asteroidsfx.asteroidentity.AsteroidTag;
import io.asteroidsfx.bulletentity.BulletTag;
import io.asteroidsfx.collision.CollisionEvent;
import io.asteroidsfx.common.*;
import io.asteroidsfx.common.System;
import io.asteroidsfx.common.event.EventBus;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.velocitycomponent.VelocityComponent;

import java.util.List;

public class AsteroidBulletCollisionResponseSystem extends System{

    public AsteroidBulletCollisionResponseSystem(EventBus eventBus){
        eventBus.subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(CollisionEvent event){
        Entity asteroid = event.getEntityWith(AsteroidTag.class);
        Entity bullet = event.getEntityWith(BulletTag.class);

        if(asteroid != null && bullet != null) {
            asteroid.toBeRemoved = true;
            bullet.toBeRemoved = true;

            int asteroidSize = asteroid.getComponent(AsteroidSizeComponent.class).size;
            if (asteroidSize > AsteroidSizeComponent.SMALL){
                for(int i = 0; i < 2; i++){
                    AsteroidEntity newAsteroid = new AsteroidEntity(asteroid.getComponent(PositionComponent.class).pos.copy());
                    newAsteroid.getComponent(VelocityComponent.class).vel =
                            bullet.getComponent(VelocityComponent.class).vel.copy()
                                    .rotate((float) Math.toRadians(60 + i * 240))
                                    .setMag(asteroid.getComponent(VelocityComponent.class).vel.mag());
                    newAsteroid.getComponent(AsteroidSizeComponent.class).size = asteroidSize - 1;
                    World.getInstance().queueAddEntity(newAsteroid);
                }
            }

        }
    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of();
    }

    @Override
    public void tick(float dt, List<Entity> entities) {

    }
}