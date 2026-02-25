package io.asteroidsfx.asteroidbulletcollisionresponsesystem;

import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;

public class AsteroidBulletCollisionResponseSystemProvider implements SystemSpi {

    @Override
    public void start(World world) {
        world.addSystem(new AsteroidBulletCollisionResponseSystem(world.getEventBus()));
    }
}
