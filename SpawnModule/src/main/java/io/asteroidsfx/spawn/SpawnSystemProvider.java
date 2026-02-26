package io.asteroidsfx.spawn;

import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;

public class SpawnSystemProvider implements SystemSpi {
    @Override
    public void start(World world) {
        world.addSystem(new SpawnSystem(world.getEventBus()));
    }
}
