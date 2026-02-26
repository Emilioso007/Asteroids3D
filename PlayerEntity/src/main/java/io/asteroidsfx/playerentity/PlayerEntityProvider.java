package io.asteroidsfx.playerentity;

import io.asteroidsfx.common.EntitySpi;
import io.asteroidsfx.common.Vector;
import io.asteroidsfx.common.World;

public class PlayerEntityProvider implements EntitySpi {
    @Override
    public void start(World world) {
        world.addEntity(new PlayerEntity(new Vector((float) world.width /2, (float) world.height /2)));
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
