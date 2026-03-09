package io.asteroidsjaylib.player;

import io.asteroidsjaylib.coincommon.CoinTag;
import io.asteroidsjaylib.collisioncommon.CollisionEvent;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.ownershipcommon.OwnershipComponent;
import io.asteroidsjaylib.playercommon.PlayerTag;

public class PlayerCollisionResponseSystem extends ResponseSystem {
    @Override
    public void start(World world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(World world, CollisionEvent event) {
        // If no player in collision, do nothing
        if(!event.hasEntityWith(PlayerTag.class)) return;

        BaseEntity player = event.getEntityWith(PlayerTag.class);
        BaseEntity collider = event.getOther(player);

        // If collider is also player, do nothing
        if (collider.hasComponent(PlayerTag.class)) return;
        if (collider.hasComponent(CoinTag.class)) return;

        // If collider owner is player, do nothing
        if (collider.hasComponent(OwnershipComponent.class)
                && collider.getComponent(OwnershipComponent.class).owner.hasComponent(PlayerTag.class))
            return;

        // Mark player to be removed
        player.setToBeRemoved(true);
    }
}
