package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.common.coin.CoinSPI;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public class CoinProvider implements CoinSPI {
    @Override
    public BaseEntity createCoin(Vector2D startPosition, Vector2D startVelocity, int value) {
        return new CoinEntity(startPosition, startVelocity, value);
    }
}
