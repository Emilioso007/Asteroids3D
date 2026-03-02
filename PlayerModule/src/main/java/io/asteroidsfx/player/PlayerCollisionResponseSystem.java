package io.asteroidsfx.player;

import io.asteroidsfx.Ownership.OwnershipComponent;
import io.asteroidsfx.collision.CollisionEvent;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.ResponseSystem;

public class PlayerCollisionResponseSystem extends ResponseSystem {
    @Override
    public void start(World world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(CollisionEvent event) {
        // If no player in collision, do nothing
        if(!event.hasEntityWith(PlayerTag.class)) return;

        BaseEntity player = event.getEntityWith(PlayerTag.class);
        BaseEntity collider = event.getOther(player);

        // If collider is also player, do nothing
        if (collider.hasComponent(PlayerTag.class)) return;

        // If collider owner is player, do nothing
        if (collider.hasComponent(OwnershipComponent.class)
                && collider.getComponent(OwnershipComponent.class).owner.hasComponent(PlayerTag.class))
            return;

        // Mark player to be removed
        player.setToBeRemoved(true);
    }
}
