package io.asteroidsjaylib.coincommon;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector;

public interface CoinSPI{
    BaseEntity createCoin(Vector startPosition, Vector startVelocity, int value);
}