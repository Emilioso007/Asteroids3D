package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidSPI;
import io.asteroidsjaylib.common.asteroid.AsteroidSize;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector3D;

public class AsteroidProvider implements AsteroidSPI {
    @Override
    public BaseEntity createAsteroid(Vector3D startPosition, Vector3D startVelocity, AsteroidSize size) {
        return new AsteroidEntity(startPosition, startVelocity, size);
    }
}
