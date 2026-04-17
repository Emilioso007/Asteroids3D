package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public class BulletProvider implements BulletSPI {

    @Override
    public BaseEntity CreateBullet(BaseEntity owner, Vector3D startPosition, Vector3D velocity, Quaternion rotation) {
        return new BulletEntity(owner, startPosition, velocity, rotation);
    }
}
