package io.asteroidsfx.asteroidplayercollisionresponsesystem;

import io.asteroidsfx.asteroidentity.AsteroidTag;
import io.asteroidsfx.collisionsystem.CollisionEvent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.common.event.EventBus;
import io.asteroidsfx.playerentity.PlayerTag;

import java.util.List;

public class AsteroidPlayerCollisionResponseSystem extends System {

    public AsteroidPlayerCollisionResponseSystem(EventBus eventBus){
        eventBus.subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(CollisionEvent event){
        Entity asteroid = event.getEntityWith(AsteroidTag.class);
        Entity player = event.getEntityWith(PlayerTag.class);

        if(asteroid != null && player != null) {
            player.toBeRemoved = true;
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