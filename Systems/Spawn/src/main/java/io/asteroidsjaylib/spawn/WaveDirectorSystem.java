package io.asteroidsjaylib.spawn;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.asteroid.AsteroidPart;
import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.enemy.EnemySPI;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.ITimeProvider;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Random;
import java.util.ServiceLoader;

public class WaveDirectorSystem extends BulkSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ITimeProvider timeProvider;

    private AsteroidSPI asteroidSPI;
    private EnemySPI enemySPI;

    @Override
    public void start(IWorld world) {
        this.priority(85);

        asteroidSPI = ServiceLoader.load(AsteroidSPI.class).findFirst().orElse(null);
        enemySPI = ServiceLoader.load(EnemySPI.class).findFirst().orElse(null);

    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {
        for (BaseEntity entity : entities){
            if (entity.hasAll(AsteroidTag.class) || entity.hasAll(EnemyTag.class)){
                return;
            }
        }

        Random random = new Random();

        if (asteroidSPI != null) {
            for (int i = 0; i < 50; i++) {

                Vector3D position = Vector3D.random().multiply(world.getWorldSize()/2);

                eventPublisher.publishEvent(new SpawnEvent(asteroidSPI.createAsteroid(
                                position,
                                new Vector3D(-50 + random.nextFloat() * 100, -50 + random.nextFloat() * 100, -50 + random.nextFloat() * 100),
                                Quaternion.randomQuaternion(),
                                AsteroidPart.Type.Full,
                                timeProvider.getTime())));
            }
        }

        if (enemySPI != null) {
            for (int i = 0; i < 10; i++) {

                Vector3D position = Vector3D.random().multiply(world.getWorldSize()/2);

                eventPublisher.publishEvent(new SpawnEvent(enemySPI.createEnemy(position)));
            }
        }

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of();
    }

}
