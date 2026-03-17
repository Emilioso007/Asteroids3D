package io.asteroidsjaylib.background;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.EntitySpi;

public class BackgroundProvider implements EntitySpi {
    @Override
    public void start(IWorld world) {
        world.addEntity(new BackgroundEntity());
    }

}
