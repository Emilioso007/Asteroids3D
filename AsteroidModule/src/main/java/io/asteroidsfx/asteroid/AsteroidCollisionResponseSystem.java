package io.asteroidsfx.asteroid;

import io.asteroidsfx.collision.CollisionEvent;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.ResponseSystem;
import io.asteroidsfx.physics.component.PositionComponent;
import io.asteroidsfx.physics.component.VelocityComponent;

public class AsteroidCollisionResponseSystem extends ResponseSystem {
    @Override
    public void start(World world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(World world, CollisionEvent event) {
        // If no asteroid in collision, do nothing
        if(!event.hasEntityWith(AsteroidTag.class)) return;

        BaseEntity asteroid = event.getEntityWith(AsteroidTag.class);
        BaseEntity collider = event.getOther(asteroid);

        // If collider is also asteroid, do nothing
        if (collider.hasComponent(AsteroidTag.class)) return;

        // Mark asteroid to be removed
        asteroid.setToBeRemoved(true);

        // Optionally split asteroid
        int asteroidSize = asteroid.getComponent(AsteroidSizeComponent.class).size;
        if (asteroidSize > AsteroidSizeComponent.SMALL){
            for(int i = 0; i < 2; i++){
                AsteroidEntity newAsteroid = new AsteroidEntity(asteroid.getComponent(PositionComponent.class).pos.copy());
                newAsteroid.getComponent(VelocityComponent.class).vel =
                        collider.getComponent(VelocityComponent.class).vel.copy()
                                .rotate(Math.toRadians(60 + i * 240))
                                .setMag(asteroid.getComponent(VelocityComponent.class).vel.mag());
                newAsteroid.getComponent(AsteroidSizeComponent.class).size = asteroidSize - 1;
                world.queueAddEntity(newAsteroid);
            }
        }

    }
}
