package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public class BulletProvider implements BulletSPI {

    @Override
    public BaseEntity CreateBullet(BaseEntity owner, Vector2D startPosition, Vector2D velocity) {
        return new BulletEntity(owner, startPosition, velocity);
    }
}
