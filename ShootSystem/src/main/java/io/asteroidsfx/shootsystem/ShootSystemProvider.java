package io.asteroidsfx.shootsystem;

import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;

public class ShootSystemProvider implements SystemSpi {
    @Override
    public void start(World world) {
        world.addSystem(new ShootSystem());
    }
}
