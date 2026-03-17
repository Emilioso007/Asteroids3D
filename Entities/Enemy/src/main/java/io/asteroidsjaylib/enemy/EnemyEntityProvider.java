package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.World;

public class EnemyEntityProvider implements EntitySpi {
    @Override
    public void start(World world) {
        world.addEntity(new EnemyEntity(world));
    }

}
