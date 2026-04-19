package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import org.springframework.context.event.EventListener;

public class PlayerCollisionResponseSystem extends ResponseSystem {

    @EventListener
    private void handleCollision(CollisionEvent event) {
        // If no player in collision, do nothing
        if(!event.hasEntityWith(PlayerTag.class)) return;

        BaseEntity player = event.getEntityWith(PlayerTag.class);
        BaseEntity collider = event.getOther(player);

        // If collider is also player, do nothing
        if (collider.hasComponents(PlayerTag.class)) return;
        if (collider.hasComponents(CrystalTag.class)) return;

        // If collider owner is player, do nothing
        var ownership = collider.getComponent(OwnershipComponent.class);
        if (ownership != null && ownership.owner.hasComponents(PlayerTag.class)) {
            return;
        }

        // Mark player to be removed
        player.setToBeRemoved(true);
    }
}
