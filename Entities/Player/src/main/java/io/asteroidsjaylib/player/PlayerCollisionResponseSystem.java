package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.common.event.EventSubscription;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import io.asteroidsjaylib.common.player.PlayerTag;

import java.util.List;

public class PlayerCollisionResponseSystem implements EventSubscriberSPI {
    @Override
    public List<EventSubscription<? extends BaseEvent>> getEventSubscriptions() {
        return List.of(new EventSubscription<>(CollisionEvent.class, this::handleCollision));
    }

    private void handleCollision(IWorld world, CollisionEvent event) {
        // If no player in collision, do nothing
        if(!event.hasEntityWith(PlayerTag.class)) return;

        BaseEntity player = event.getEntityWith(PlayerTag.class);
        BaseEntity collider = event.getOther(player);

        // If collider is also player, do nothing
        if (collider.hasComponents(PlayerTag.class)) return;
        if (collider.hasComponents(CoinTag.class)) return;

        // If collider owner is player, do nothing
        var ownership = collider.getComponent(OwnershipComponent.class);
        if (ownership != null && ownership.owner.hasComponents(PlayerTag.class)) {
            return;
        }

        // Mark player to be removed
        player.setToBeRemoved(true);
    }
}
