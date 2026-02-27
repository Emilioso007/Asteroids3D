package io.asteroidsfx.asteroidentity;

import io.asteroidsfx.common.EntitySpi;
import io.asteroidsfx.common.Vector;
import io.asteroidsfx.common.World;

import java.util.Random;

public class AsteroidProvider implements EntitySpi {
    @Override
    public void start(World world) {
        Random random = new Random();
        Vector startPosition = new Vector(random.nextFloat(0, world.width), random.nextFloat(0, world.height));
        world.addEntity(new AsteroidEntity(startPosition));
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
