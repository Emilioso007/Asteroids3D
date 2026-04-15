package io.asteroidsjaylib.common.coin;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector3D;

public interface CoinSPI{
    BaseEntity createCoin(Vector3D startPosition, int value);
}