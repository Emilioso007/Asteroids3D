package io.asteroidsjaylib.spawn;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidType;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.enemy.EnemySPI;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;
import java.util.Random;
import java.util.ServiceLoader;

public class WaveDirectorSystem extends BulkSystem {

    private AsteroidSPI asteroidSPI;
    private EnemySPI enemySPI;

    @Override
    public void start(IWorld world) {
        this.setPriority(85);

        asteroidSPI = ServiceLoader.load(AsteroidSPI.class).findFirst().orElseThrow();
        enemySPI = ServiceLoader.load(EnemySPI.class).findFirst().orElseThrow();

    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {
        for (BaseEntity entity : entities){
            if (entity.hasComponents(AsteroidTag.class) || entity.hasComponents(EnemyTag.class)){
                return;
            }
        }

        var playerOpt = world.getEntitiesWith(PlayerTag.class).stream().findFirst();
        if (playerOpt.isEmpty()) return;

        //Vector2D playerLocation = playerOpt.get().getComponent(PositionComponent.class).orElseThrow().pos;

        Random random = new Random();

        for(int i = 0; i < 5; i++){
            world.getEventBus().publish(world,
                    new SpawnEvent(asteroidSPI.createAsteroid(
                                    new Vector3D(1000, 1000, 0),
                                    new Vector3D(-50+random.nextFloat()*100, -50+random.nextFloat()*100, -50+random.nextFloat()*100),
                                    Quaternion.randomQuaternion(),
                                    AsteroidType.Full)));
        }

        for (int i = 0; i < 1; i++){
            world.getEventBus().publish(world,
                    new SpawnEvent(enemySPI.createEnemy(new Vector3D(1000, 0, 0))));
        }

    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of();
    }

}
