package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;

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


        // Mark asteroid to be removed
        asteroid.setToBeRemoved(true);

        /*
        // Optionally split asteroid
        AsteroidSize asteroidSize = asteroid.getComponent(AsteroidSizeComponent.class).map(c -> c.size).orElseThrow();
        if (asteroidSize.ordinal() > AsteroidSize.SMALL.ordinal()){
            for(int i = 0; i < 2; i++){

                Vector2D position = asteroid.getComponent(PositionComponent.class).map(c -> c.pos.copy()).orElseThrow();

                float magnitude = (float) (50 + 200 * Math.random());

                Vector2D velocity = collider.getComponent(VelocityComponent.class).map(c -> c.vel.copy()).orElse(Vector2D.randomVector(1));
                velocity.rotate(60 + i * 240).setMag(magnitude);

                AsteroidEntity newAsteroid = new AsteroidEntity(position, velocity, AsteroidSize.values()[asteroidSize.ordinal() - 1]);
                world.queueAddEntity(newAsteroid);
            }
        }

        // Publish event
        CoinSPI coinSPI = ServiceLoader.load(CoinSPI.class).findFirst().orElseThrow();
        Vector2D pos = asteroid.getComponent(PositionComponent.class).orElseThrow().pos.copy();
        Vector2D vel = Vector2D.randomVector(25);
        world.getEventBus().publish(world, new SpawnEvent(coinSPI.createCoin(pos, vel, asteroidSize.ordinal()+1)));
*/
    }
}
