
import io.asteroidsjaylib.coin.CoinCollisionResponseSystem;
import io.asteroidsjaylib.coin.CoinProvider;
import io.asteroidsjaylib.common.coin.CoinSPI;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;

module Coin {
    requires Common;
    requires CommonCollision;
    requires CommonPlayer;
    requires CommonScore;
    requires CommonCoin;
    requires CommonRender;
    requires CommonPhysics2D;
    requires CommonOutOfBounds;
    requires jaylib;
    provides CoinSPI with CoinProvider;
    provides EventSubscriberSPI with CoinCollisionResponseSystem;
}