package io.asteroidsjaylib.common.bullet;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public interface BulletSPI {
    BaseEntity CreateBullet(BaseEntity owner, Vector2D startPosition, Vector2D velocity);
}
