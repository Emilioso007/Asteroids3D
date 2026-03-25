package io.asteroidsjaylib.spawn;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidSize;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.enemy.EnemySPI;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.List;
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
            if (entity.hasComponent(AsteroidTag.class) || entity.hasComponent(EnemyTag.class)){
                return;
            }
        }

        var playerOpt = world.getEntitiesWith(PlayerTag.class).stream().findFirst();
        if (playerOpt.isEmpty()) return;

        Vector2D playerLocation = playerOpt.get().getComponent(PositionComponent.class).orElseThrow().pos;

        for(int i = 0; i < 5; i++){
            world.getEventBus().publish(world,
                    new SpawnEvent(asteroidSPI.createAsteroid(
                                    playerLocation.copy().add((float) world.getWidth() /2, (float) world.getHeight() /2),
                                    Vector2D.randomVector((float) (50+Math.random()*200)),
                                    AsteroidSize.LARGE)));
        }

        for (int i = 0; i < 2; i++){
            world.getEventBus().publish(world,
                    new SpawnEvent(enemySPI.createEnemy(
                            playerLocation.copy().add((float) (world.getWidth() /2f -100 + Math.random()*200) , (float) (Math.random()*world.getHeight())))));
        }

    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of();
    }

}
