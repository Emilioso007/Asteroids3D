package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidSize;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public class AsteroidProvider implements AsteroidSPI {
    @Override
    public BaseEntity createAsteroid(Vector2D startPosition, Vector2D startVelocity, AsteroidSize size) {
        return new AsteroidEntity(startPosition, startVelocity, size);
    }
}
