package io.asteroidsfx.asteroidplayercollisionresponsesystem;

import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;

public class AsteroidPlayerCollisionResponseSystemProvider implements SystemSpi {
    @Override
    public void start(World world) {
        world.addSystem(new AsteroidPlayerCollisionResponseSystem(world.getEventBus()));
    }
}
