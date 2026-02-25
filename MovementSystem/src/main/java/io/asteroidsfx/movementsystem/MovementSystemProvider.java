package io.asteroidsfx.movementsystem;

import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;

public class MovementSystemProvider implements SystemSpi {
    @Override
    public void start(World world) {
        world.addSystem(new MovementSystem());
    }
}
