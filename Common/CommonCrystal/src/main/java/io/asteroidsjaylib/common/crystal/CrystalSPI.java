package io.asteroidsjaylib.common.crystal;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public interface CrystalSPI {
    BaseEntity createCrystal(Vector3D startPosition, Quaternion rotation, int value);
}