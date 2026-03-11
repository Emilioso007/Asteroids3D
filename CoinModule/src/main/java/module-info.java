import io.asteroidsjaylib.coin.CoinCollisionResponseSystem;
import io.asteroidsjaylib.coin.CoinProvider;
import io.asteroidsjaylib.coincommon.CoinSPI;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Coin {
    requires Common;
    requires CoinCommon;
    requires PhysicsCommon;
    requires RenderCommon;
    requires jaylib;
    requires CollisionCommon;
    requires PlayerCommon;
    requires ScoreCommon;
    requires OutOfBoundsCommon;

    provides CoinSPI with CoinProvider;
    provides BaseSystem with CoinCollisionResponseSystem;
}