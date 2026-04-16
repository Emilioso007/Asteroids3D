package io.asteroidsjaylib.crystal;

import io.asteroidsjaylib.common.crystal.CrystalSPI;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public class CrystalProvider implements CrystalSPI {
    @Override
    public BaseEntity createCrystal(Vector3D startPosition, Quaternion rotation, int value) {
        return new CrystalEntity(startPosition, rotation, value);
    }
}
