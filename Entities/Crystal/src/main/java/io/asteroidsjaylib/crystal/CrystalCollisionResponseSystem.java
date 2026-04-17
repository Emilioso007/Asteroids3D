package io.asteroidsjaylib.crystal;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.common.event.EventSubscription;
import io.asteroidsjaylib.common.player.PlayerTag;

import java.util.List;

public class CrystalCollisionResponseSystem implements EventSubscriberSPI {
    @Override
    public List<EventSubscription<? extends BaseEvent>> getEventSubscriptions() {
        return List.of(new EventSubscription<>(CollisionEvent.class, this::handleCollision));
    }

    private void handleCollision(IWorld world, CollisionEvent collisionEvent) {
        if(!collisionEvent.hasEntityWith(CrystalTag.class)) return;

        BaseEntity crystal = collisionEvent.getEntityWith(CrystalTag.class);
        BaseEntity other = collisionEvent.getOther(crystal);

        if(!other.hasComponents(PlayerTag.class)) return;

        crystal.setToBeRemoved(true);
    }
}
