package io.asteroidsjaylib.common.enemy;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector3D;

public interface EnemySPI {
    BaseEntity createEnemy(Vector3D startPosition);
}
