package io.asteroidsjaylib.common.coin;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public interface CoinSPI{
    BaseEntity createCoin(Vector2D startPosition, Vector2D startVelocity, int value);
}