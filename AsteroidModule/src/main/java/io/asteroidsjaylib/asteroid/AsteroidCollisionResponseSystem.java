package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.asteroidcommon.AsteroidSizeComponent;
import io.asteroidsjaylib.asteroidcommon.AsteroidTag;
import io.asteroidsjaylib.coincommon.CoinSPI;
import io.asteroidsjaylib.coincommon.CoinTag;
import io.asteroidsjaylib.collisioncommon.CollisionEvent;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.physicscommon.*;
import io.asteroidsjaylib.spawncommon.SpawnEvent;

import java.util.ServiceLoader;

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
        if (collider.hasComponent(CoinTag.class)) return;

        // Mark asteroid to be removed
        asteroid.setToBeRemoved(true);

        // Optionally split asteroid
        int asteroidSize = asteroid.getComponent(AsteroidSizeComponent.class).size;
        if (asteroidSize > AsteroidSizeComponent.SMALL){
            for(int i = 0; i < 2; i++){
                AsteroidEntity newAsteroid = new AsteroidEntity(asteroid.getComponent(PositionComponent.class).pos.copy(), asteroidSize - 1);
                newAsteroid.getComponent(VelocityComponent.class).vel =
                        collider.getComponent(VelocityComponent.class).vel.copy()
                                .rotate(Math.toRadians(60 + i * 240))
                                .setMag(asteroid.getComponent(VelocityComponent.class).vel.mag());
                world.queueAddEntity(newAsteroid);
            }
        }

        // Publish event
        CoinSPI coinSPI = ServiceLoader.load(CoinSPI.class).findFirst().orElseThrow();
        Vector pos = asteroid.getComponent(PositionComponent.class).pos.copy();
        Vector vel = Vector.randomVector(25);
        world.getEventBus().publish(world, new SpawnEvent(coinSPI.createCoin(pos, vel, asteroidSize+1)));

    }
}
