package io.asteroidsjaylib.background;

import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.EntitySpi;

public class BackgroundProvider implements EntitySpi {
    @Override
    public void start(World world) {
        world.addEntity(new BackgroundEntity());
    }

}
