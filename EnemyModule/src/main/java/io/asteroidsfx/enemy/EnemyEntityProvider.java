package io.asteroidsfx.enemy;

import io.asteroidsfx.common.ecs.EntitySpi;
import io.asteroidsfx.common.World;

public class EnemyEntityProvider implements EntitySpi {
    @Override
    public void start(World world) {
        world.addEntity(new EnemyEntity(world));
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
