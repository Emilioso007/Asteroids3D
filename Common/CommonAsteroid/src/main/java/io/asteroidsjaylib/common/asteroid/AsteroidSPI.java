package io.asteroidsjaylib.common.asteroid;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public interface AsteroidSPI {
    BaseEntity createAsteroid(Vector3D startPosition, Vector3D startVelocity, Quaternion rotation, AsteroidType size);
}
