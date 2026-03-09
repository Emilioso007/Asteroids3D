package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.coincommon.CoinTag;
import io.asteroidsjaylib.collisioncommon.CollisionEvent;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.playercommon.PlayerTag;
import io.asteroidsjaylib.scorecommon.IncrementScoreEvent;

public class CoinCollisionResponseSystem extends ResponseSystem {
    @Override
    public void start(World world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(World world, CollisionEvent collisionEvent) {
        if(!collisionEvent.hasEntityWith(CoinTag.class)) return;

        BaseEntity coin = collisionEvent.getEntityWith(CoinTag.class);
        BaseEntity other = collisionEvent.getOther(coin);

        if(!other.hasComponent(PlayerTag.class)) return;

        coin.setToBeRemoved(true);
        world.getEventBus().publish(world, new IncrementScoreEvent(coin.getComponent(CoinTag.class).value));

    }
}
