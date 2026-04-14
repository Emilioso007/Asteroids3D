package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.common.event.EventSubscription;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.score.IncrementScoreEvent;

import java.util.List;

public class CoinCollisionResponseSystem implements EventSubscriberSPI {
    @Override
    public List<EventSubscription<? extends BaseEvent>> getEventSubscriptions() {
        return List.of(new EventSubscription<>(CollisionEvent.class, this::handleCollision));
    }

    private void handleCollision(IWorld world, CollisionEvent collisionEvent) {
        if(!collisionEvent.hasEntityWith(CoinTag.class)) return;

        BaseEntity coin = collisionEvent.getEntityWith(CoinTag.class);
        BaseEntity other = collisionEvent.getOther(coin);

        if(!other.hasComponents(PlayerTag.class)) return;

        coin.setToBeRemoved(true);
        world.getEventBus().publish(world, new IncrementScoreEvent(coin.getComponent(CoinTag.class).value));

    }
}
