package io.asteroidsfx.outofboundssystem;

import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;

public class OutOfBoundsSystemProvider implements SystemSpi {
    @Override
    public void start(World world) {
        world.addSystem(new OutOfBoundsSystem(world.width, world.height));
    }
}
