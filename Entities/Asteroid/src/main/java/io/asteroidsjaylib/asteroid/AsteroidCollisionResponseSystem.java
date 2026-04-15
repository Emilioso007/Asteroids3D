package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidType;
import io.asteroidsjaylib.common.asteroid.AsteroidTypeComponent;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.crystal.CrystalSPI;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.common.event.EventSubscription;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;
import java.util.ServiceLoader;

public class AsteroidCollisionResponseSystem implements EventSubscriberSPI {

    @Override
    public List<EventSubscription<? extends BaseEvent>> getEventSubscriptions() {
        return List.of(new EventSubscription<>(CollisionEvent.class, this::handleCollision));
    }

    private void handleCollision(IWorld world, CollisionEvent event) {
        // If no asteroid in collision, do nothing
        if(!event.hasEntityWith(AsteroidTag.class)) return;

        BaseEntity asteroid = event.getEntityWith(AsteroidTag.class);
        BaseEntity collider = event.getOther(asteroid);

        // If collider is also asteroid, do nothing
        if (collider.hasComponents(AsteroidTag.class)) return;
        if (collider.hasComponents(EnemyTag.class)) return;
        if (collider.hasComponents(CrystalTag.class)) return;

        if (collider.isToBeRemoved()) return;


        // Mark asteroid to be removed
        asteroid.setToBeRemoved(true);

        // Split asteroid in two and reveal crystal inside
        AsteroidSPI asteroidSPI = ServiceLoader.load(AsteroidSPI.class).findFirst().orElseThrow();

        AsteroidType type = asteroid.getComponent(AsteroidTypeComponent.class).type;

        if (type == AsteroidType.Full){

            world.getEventBus().publish(world,
                    new SpawnEvent(asteroidSPI.createAsteroid(
                            asteroid.getComponent(PositionComponent.class).pos.copy(),
                            asteroid.getComponent(RotationComponent.class).quaternion.getAxis().copy().mult(10),
                            AsteroidType.Top
                    )));

            world.getEventBus().publish(world,
                    new SpawnEvent(asteroidSPI.createAsteroid(
                            asteroid.getComponent(PositionComponent.class).pos.copy(),
                            asteroid.getComponent(RotationComponent.class).quaternion.getAxis().copy().mult(-10),
                            AsteroidType.Bottom
                    )));

        }

        CrystalSPI crystalSPI = ServiceLoader.load(CrystalSPI.class).findFirst().orElseThrow();
        Vector3D pos = asteroid.getComponent(PositionComponent.class).pos.copy();
        world.getEventBus().publish(world, new SpawnEvent(crystalSPI.createCrystal(pos, type.ordinal()+1)));

    }

}
