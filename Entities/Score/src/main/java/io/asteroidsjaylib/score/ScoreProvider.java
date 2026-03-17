package io.asteroidsjaylib.score;

import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.EntitySpi;

public class ScoreProvider implements EntitySpi {
    @Override
    public void start(World world) {
        world.addEntity(new ScoreEntity());
    }

}
