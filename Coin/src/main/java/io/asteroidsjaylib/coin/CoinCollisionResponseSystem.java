package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.score.IncrementScoreEvent;

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
        world.getEventBus().publish(world, new IncrementScoreEvent(coin.getComponent(CoinTag.class).orElseThrow().value));

    }
}
