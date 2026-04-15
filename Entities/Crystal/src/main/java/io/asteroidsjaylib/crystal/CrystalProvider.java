package io.asteroidsjaylib.crystal;

import io.asteroidsjaylib.common.crystal.CrystalSPI;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector3D;

public class CrystalProvider implements CrystalSPI {
    @Override
    public BaseEntity createCrystal(Vector3D startPosition, int value) {
        return new CrystalEntity(startPosition, value);
    }
}
