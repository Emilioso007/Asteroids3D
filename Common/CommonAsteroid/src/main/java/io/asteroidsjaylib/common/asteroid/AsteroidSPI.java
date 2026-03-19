package io.asteroidsjaylib.common.asteroid;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public interface AsteroidSPI {
    BaseEntity createAsteroid(Vector2D startPosition, Vector2D startVelocity, AsteroidSize size);
}
