package io.asteroidsfx.asteroidplayercollisionresponsesystem;

import io.asteroidsfx.asteroidentity.AsteroidTag;
import io.asteroidsfx.collision.CollisionEvent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.system.IteratingSystemECS;
import io.asteroidsfx.common.system.SystemECS;
import io.asteroidsfx.common.event.EventBus;
import io.asteroidsfx.playerentity.PlayerTag;

import java.util.List;

public class AsteroidPlayerCollisionResponseSystem extends IteratingSystemECS {


    @Override
    public void start(World world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
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
    public void update(List<Entity> entities, double deltaTime) {

    }

    @Override
    public void processEntity(Entity entity, double deltaTime) {

    }

}