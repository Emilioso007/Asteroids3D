package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.IWorld;

public class EnemyEntityProvider implements EntitySpi {
    @Override
    public void start(IWorld world) {
        world.addEntity(new EnemyEntity(world));
    }

}
