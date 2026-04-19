package io.asteroidsjaylib.crystal;

import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.player.PlayerTag;
import org.springframework.context.event.EventListener;

public class CrystalCollisionResponseSystem extends ResponseSystem {

    @EventListener
    private void handleCollision(CollisionEvent collisionEvent) {
        if(!collisionEvent.hasEntityWith(CrystalTag.class)) return;

        BaseEntity crystal = collisionEvent.getEntityWith(CrystalTag.class);
        BaseEntity other = collisionEvent.getOther(crystal);

        if(!other.hasComponents(PlayerTag.class)) return;

        crystal.setToBeRemoved(true);
    }
}
