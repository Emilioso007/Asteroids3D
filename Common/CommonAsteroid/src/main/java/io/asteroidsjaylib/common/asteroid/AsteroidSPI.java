package io.asteroidsjaylib.common.asteroid;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector3D;

public interface AsteroidSPI {
    BaseEntity createAsteroid(Vector3D startPosition, Vector3D startVelocity, AsteroidSize size, Raylib.Shader shader);
}
