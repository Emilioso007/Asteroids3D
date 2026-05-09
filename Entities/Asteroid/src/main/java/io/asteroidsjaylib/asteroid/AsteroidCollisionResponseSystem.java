package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidPart;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.crystal.CrystalSPI;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Rotation;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.ITimeProvider;
import io.asteroidsjaylib.common.util.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.ServiceLoader;

public class AsteroidCollisionResponseSystem extends ResponseSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ITimeProvider timeProvider;

    private final AsteroidSPI asteroidSPI;
    private final CrystalSPI crystalSPI;

    public AsteroidCollisionResponseSystem() {
        this.asteroidSPI = ServiceLoader.load(AsteroidSPI.class).findFirst().orElse(null);
        this.crystalSPI = ServiceLoader.load(CrystalSPI.class).findFirst().orElse(null);
    }

    @Override
    public void start(IWorld world) {

    }

    @EventListener
    private void handleCollision(CollisionEvent event) {

        // Check if collision is valid/our concern.
        if(!event.hasEntityWith(AsteroidTag.class)) return;

        BaseEntity asteroid = event.getEntityWith(AsteroidTag.class);
        BaseEntity collider = event.getOther(asteroid);

        if (collider.removed()
                || collider.hasAny(
                    AsteroidTag.class,
                    EnemyTag.class,
                    CrystalTag.class)
        ) return;

        // Destroy asteroid on valid hit
        asteroid.removed(true);

        Position position = asteroid.get(Position.class);
        Rotation rotation = asteroid.get(Rotation.class);

        assert position != null;
        assert rotation != null;

        // Split asteroid into two shells. Only possible if asteroidSPI is not null.
        if (asteroidSPI != null) {

            AsteroidPart asteroidPart = asteroid.get(AsteroidPart.class);
            assert asteroidPart != null;

            AsteroidPart.Type type = asteroidPart.type();
            if (type == AsteroidPart.Type.Full) {

                eventPublisher.publishEvent(new SpawnEvent(asteroidSPI.createAsteroid(
                                position.vector().copy(),
                                rotation.quaternion().rotateVector(new Vector3D(0, 0, 1)).multiply(10),
                                rotation.quaternion().copy(),
                                AsteroidPart.Type.Top,
                                timeProvider.getTime()
                        )));

                eventPublisher.publishEvent(new SpawnEvent(asteroidSPI.createAsteroid(
                                position.vector().copy(),
                                rotation.quaternion().rotateVector(new Vector3D(0, 0, 1)).multiply(-10),
                                rotation.quaternion().copy(),
                                AsteroidPart.Type.Bottom,
                                timeProvider.getTime()
                        )));

            }
        }

        // Spawn crystal as the destroyed asteroids position. Only possible if crystalSPI is not null.
        if (crystalSPI != null) {
            Vector3D pos = position.vector().copy();
            eventPublisher.publishEvent(new SpawnEvent(crystalSPI.createCrystal(pos, rotation.quaternion())));
        }
    }

}
