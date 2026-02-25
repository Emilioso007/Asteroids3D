package io.asteroidsfx.rotatesystem;

import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;

public class RotateSystemProvider implements SystemSpi {
    @Override
    public void start(World world) {
        world.addSystem(new RotateSystem());
    }
}
