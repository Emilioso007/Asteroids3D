package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.common.event.EventSubscription;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import io.asteroidsjaylib.common.score.IncrementScoreEvent;

import java.util.List;

public class EnemyCollisionResponseSystem implements EventSubscriberSPI {
    @Override
    public List<EventSubscription<? extends BaseEvent>> getEventSubscriptions() {
        return List.of(new EventSubscription<>(CollisionEvent.class, this::handleCollision));
    }

    private void handleCollision(IWorld world, CollisionEvent event) {
        // If no enemy in collision, do nothing
        if(!event.hasEntityWith(EnemyTag.class)) return;

        BaseEntity enemy = event.getEntityWith(EnemyTag.class);
        BaseEntity collider = event.getOther(enemy);

        // If collider is also enemy, do nothing
        if (collider.hasComponents(EnemyTag.class)) return;
        if (collider.hasComponents(CrystalTag.class)) return;

        // If collider owner is enemy, do nothing
        var ownership = collider.getComponent(OwnershipComponent.class);
        if (ownership != null && ownership.owner.hasComponents(EnemyTag.class)) {
            return;
        }

        // Mark enemy to be removed
        enemy.setToBeRemoved(true);

        world.getEventBus().publish(world, new IncrementScoreEvent(5));
    }
}
