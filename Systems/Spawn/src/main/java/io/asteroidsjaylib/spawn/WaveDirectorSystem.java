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
import java.util.stream.Collectors;

public class WaveDirectorSystem extends BulkSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ITimeProvider timeProvider;

    private List<AsteroidSPI> asteroidProviders;
    private List<EnemySPI> enemyProviders;

    private Random random;

    @Override
    public void start(IWorld world) {
        this.priority(85);

        this.random = new Random();

        asteroidProviders = ServiceLoader.load(AsteroidSPI.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());

        enemyProviders = ServiceLoader.load(EnemySPI.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());

    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {
        for (BaseEntity entity : entities){
            if (entity.hasAny(AsteroidTag.class, EnemyTag.class)){
                // There are more asteroids/enemies left, don't spawn next wave yet!
                return;
            }
        }

        if (!asteroidProviders.isEmpty()) {
            for (int i = 0; i < 500; i++) {

                Vector3D position = Vector3D.random().multiply(world.getWorldSize()/2);

                AsteroidSPI randomProvider = asteroidProviders.get(random.nextInt(asteroidProviders.size()));
                eventPublisher.publishEvent(new SpawnEvent(randomProvider.createAsteroid(
                                position,
                                new Vector3D(-50 + random.nextFloat() * 100, -50 + random.nextFloat() * 100, -50 + random.nextFloat() * 100),
                                Quaternion.randomQuaternion(),
                                AsteroidPart.Type.Full,
                                timeProvider.getTime())));
            }
        }

        if (!enemyProviders.isEmpty()) {
            for (int i = 0; i < 10; i++) {

                Vector3D position = Vector3D.random().multiply(world.getWorldSize()/2);

                EnemySPI randomProvider = enemyProviders.get(random.nextInt(enemyProviders.size()));
                eventPublisher.publishEvent(new SpawnEvent(randomProvider.createEnemy(position)));
            }
        }

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of();
    }

}
