package io.asteroidsfx.playerentity;

import io.asteroidsfx.common.EntitySpi;
import io.asteroidsfx.common.World;

public class PlayerEntityProvider implements EntitySpi {
    @Override
    public void start(World world) {
        world.addEntity(new PlayerEntity(world.width/2, world.height/2));
    }
}
