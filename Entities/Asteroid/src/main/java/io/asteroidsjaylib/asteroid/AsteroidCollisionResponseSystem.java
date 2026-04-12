package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidSize;
import io.asteroidsjaylib.common.asteroid.AsteroidSizeComponent;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.Random;
import java.util.ServiceLoader;

public class AsteroidCollisionResponseSystem extends ResponseSystem {
    @Override
    public void start(IWorld world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(IWorld world, CollisionEvent event) {
        // If no asteroid in collision, do nothing
        if(!event.hasEntityWith(AsteroidTag.class)) return;

        BaseEntity asteroid = event.getEntityWith(AsteroidTag.class);
        BaseEntity collider = event.getOther(asteroid);

        // If collider is also asteroid, do nothing
        if (collider.hasComponents(AsteroidTag.class)) return;
        if (collider.hasComponents(CoinTag.class)) return;

        if (collider.isToBeRemoved()) return;


        // Mark asteroid to be removed
        asteroid.setToBeRemoved(true);

        // Optionally split asteroid
        Random random = new Random();
        AsteroidSPI asteroidSPI = ServiceLoader.load(AsteroidSPI.class).findFirst().orElseThrow();

        AsteroidSize asteroidSize = asteroid.getComponent(AsteroidSizeComponent.class).map(c -> c.size).orElseThrow();
        if (asteroidSize.ordinal() > AsteroidSize.SMALL.ordinal()){
            for(int i = 0; i < 2; i++){
                world.getEventBus().publish(world,
                        new SpawnEvent(asteroidSPI.createAsteroid(
                                asteroid.getComponent(PositionComponent.class).orElseThrow().pos.copy(),
                                new Vector3D(-50+random.nextFloat()*100, -50+random.nextFloat()*100, -50+random.nextFloat()*100),
                                AsteroidSize.values()[asteroidSize.ordinal() - 1],
                                world.getShader())));
            }
        }

        /*
        // Publish event
        CoinSPI coinSPI = ServiceLoader.load(CoinSPI.class).findFirst().orElseThrow();
        Vector2D pos = asteroid.getComponent(PositionComponent.class).orElseThrow().pos.copy();
        Vector2D vel = Vector2D.randomVector(25);
        world.getEventBus().publish(world, new SpawnEvent(coinSPI.createCoin(pos, vel, asteroidSize.ordinal()+1)));
        */
    }
}
