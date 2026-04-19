package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidType;
import io.asteroidsjaylib.common.asteroid.AsteroidTypeComponent;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.crystal.CrystalSPI;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.ServiceLoader;

public class AsteroidCollisionResponseSystem extends ResponseSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private final AsteroidSPI asteroidSPI;
    private final CrystalSPI crystalSPI;

    public AsteroidCollisionResponseSystem() {
        this.asteroidSPI = ServiceLoader.load(AsteroidSPI.class).findFirst().orElse(null);
        this.crystalSPI = ServiceLoader.load(CrystalSPI.class).findFirst().orElse(null);
    }

    @EventListener
    private void handleCollision(CollisionEvent event) {

        // Check if collision is valid/our concern.
        if(!event.hasEntityWith(AsteroidTag.class)) return;

        BaseEntity asteroid = event.getEntityWith(AsteroidTag.class);
        BaseEntity collider = event.getOther(asteroid);

        if (collider.hasComponents(AsteroidTag.class)) return;
        if (collider.hasComponents(EnemyTag.class)) return;
        if (collider.hasComponents(CrystalTag.class)) return;
        if (collider.isToBeRemoved()) return;

        // Base Logic: Always destroy asteroid on valid hit
        asteroid.setToBeRemoved(true);

        // Extended logic: Split asteroid into two shells. Only possible if asteroidSPI is not null.
        if (asteroidSPI != null) {

            AsteroidType type = asteroid.getComponent(AsteroidTypeComponent.class).type;
            if (type == AsteroidType.Full) {

                eventPublisher.publishEvent(new SpawnEvent(asteroidSPI.createAsteroid(
                                asteroid.getComponent(PositionComponent.class).pos.copy(),
                                asteroid.getComponent(RotationComponent.class).quaternion.rotateVector(new Vector3D(0, 0, 1)).mult(10),
                                asteroid.getComponent(RotationComponent.class).quaternion.copy(),
                                AsteroidType.Top
                        )));

                eventPublisher.publishEvent(new SpawnEvent(asteroidSPI.createAsteroid(
                                asteroid.getComponent(PositionComponent.class).pos.copy(),
                                asteroid.getComponent(RotationComponent.class).quaternion.rotateVector(new Vector3D(0, 0, 1)).mult(-10),
                                asteroid.getComponent(RotationComponent.class).quaternion.copy(),
                                AsteroidType.Bottom
                        )));

            }
        }

        // Extended logic: Spawn crystal as the destroyed asteroids position. Only possible if crystalSPI is not null.
        if (crystalSPI != null) {
            Vector3D pos = asteroid.getComponent(PositionComponent.class).pos.copy();
            eventPublisher.publishEvent(new SpawnEvent(crystalSPI.createCrystal(pos, asteroid.getComponent(RotationComponent.class).quaternion)));
        }
    }

}
