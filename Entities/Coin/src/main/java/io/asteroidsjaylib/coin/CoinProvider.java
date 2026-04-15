package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.common.coin.CoinSPI;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector3D;

public class CoinProvider implements CoinSPI {
    @Override
    public BaseEntity createCoin(Vector3D startPosition, int value) {
        return new CoinEntity(startPosition, value);
    }
}
