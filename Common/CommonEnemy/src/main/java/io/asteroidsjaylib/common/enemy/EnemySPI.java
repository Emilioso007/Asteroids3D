package io.asteroidsjaylib.common.enemy;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public interface EnemySPI {
    BaseEntity createEnemy(Vector2D startPosition);
}
