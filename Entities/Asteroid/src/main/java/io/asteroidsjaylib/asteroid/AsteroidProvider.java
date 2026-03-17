package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidSizeComponent;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.IWorld;

import java.util.Random;

public class AsteroidProvider implements EntitySpi {
    @Override
    public void start(IWorld world) {
        Random random = new Random();
        Vector2D startPosition = new Vector2D(random.nextFloat(0, world.getWidth()), random.nextFloat(0, world.getHeight()));
        world.addEntity(new AsteroidEntity(startPosition, Vector2D.fromAngle(random.nextFloat((float) (Math.PI*2))).setMag(random.nextFloat(50, 250)), AsteroidSizeComponent.LARGE));
    }

}
