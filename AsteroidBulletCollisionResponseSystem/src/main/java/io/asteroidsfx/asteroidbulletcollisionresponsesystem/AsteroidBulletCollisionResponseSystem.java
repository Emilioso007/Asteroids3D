package io.asteroidsfx.asteroidbulletcollisionresponsesystem;

import io.asteroidsfx.asteroidentity.AsteroidTag;
import io.asteroidsfx.bulletentity.BulletTag;
import io.asteroidsfx.collisionsystem.CollisionEvent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.common.event.EventBus;

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