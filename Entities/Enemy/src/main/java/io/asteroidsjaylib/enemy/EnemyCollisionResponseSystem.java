package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import org.springframework.context.event.EventListener;

public class EnemyCollisionResponseSystem extends ResponseSystem {

    @EventListener
    private void handleCollision(CollisionEvent event) {
        // If no enemy in collision, do nothing
        if(!event.hasEntityWith(EnemyTag.class)) return;

        BaseEntity enemy = event.getEntityWith(EnemyTag.class);
        BaseEntity collider = event.getOther(enemy);

        // If collider is also enemy, do nothing
        if (collider.hasComponents(EnemyTag.class)) return;
        if (collider.hasComponents(AsteroidTag.class)) return;
        if (collider.hasComponents(CrystalTag.class)) return;

        // If collider owner is enemy, do nothing
        var ownership = collider.getComponent(OwnershipComponent.class);
        if (ownership != null && ownership.owner.hasComponents(EnemyTag.class)) {
            return;
        }

        // Mark enemy to be removed
        enemy.setToBeRemoved(true);
    }
}
