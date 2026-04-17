package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.enemy.EnemySPI;
import io.asteroidsjaylib.common.util.Vector3D;

public class EnemyProvider implements EnemySPI {
    @Override
    public BaseEntity createEnemy(Vector3D startPosition) {
        return new EnemyEntity(startPosition);
    }
}
