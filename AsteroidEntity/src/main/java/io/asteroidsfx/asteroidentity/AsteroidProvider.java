package io.asteroidsfx.asteroidentity;

import io.asteroidsfx.common.EntitySpi;
import io.asteroidsfx.common.World;

public class AsteroidProvider implements EntitySpi {
    @Override
    public void start(World world) {
        world.addEntity(new AsteroidEntity(world.width, world.height));
    }
}
