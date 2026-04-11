package io.asteroidsjaylib.common.bullet;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public interface BulletSPI {
    BaseEntity CreateBullet(BaseEntity owner, Vector3D startPosition, Vector3D velocity, Quaternion rotation);
}
