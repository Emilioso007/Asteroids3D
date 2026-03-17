package io.asteroidsjaylib.score;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.EntitySpi;

public class ScoreProvider implements EntitySpi {
    @Override
    public void start(IWorld world) {
        world.addEntity(new ScoreEntity());
    }

}
