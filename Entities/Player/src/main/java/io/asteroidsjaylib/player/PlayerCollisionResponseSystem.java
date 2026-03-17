package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import io.asteroidsjaylib.common.player.PlayerTag;

public class PlayerCollisionResponseSystem extends ResponseSystem {
    @Override
    public void start(IWorld world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(IWorld world, CollisionEvent event) {
        // If no player in collision, do nothing
        if(!event.hasEntityWith(PlayerTag.class)) return;

        BaseEntity player = event.getEntityWith(PlayerTag.class);
        BaseEntity collider = event.getOther(player);

        // If collider is also player, do nothing
        if (collider.hasComponent(PlayerTag.class)) return;
        if (collider.hasComponent(CoinTag.class)) return;

        // If collider owner is player, do nothing
        var ownership = collider.getComponent(OwnershipComponent.class);
        if (ownership.isPresent() && ownership.get().owner.hasComponent(PlayerTag.class)) {
            return;
        }

        // Mark player to be removed
        player.setToBeRemoved(true);
    }
}
