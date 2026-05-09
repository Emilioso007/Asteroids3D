package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.ownership.Ownership;
import org.springframework.context.event.EventListener;

public class EnemyCollisionResponseSystem extends ResponseSystem {

    @EventListener
    private void handleCollision(CollisionEvent event) {
        // If no enemy in collision, do nothing
        if(!event.hasEntityWith(EnemyTag.class)) return;

        BaseEntity enemy = event.getEntityWith(EnemyTag.class);
        BaseEntity collider = event.getOther(enemy);

        // If collider is also enemy, do nothing
        if (collider.hasAll(EnemyTag.class)) return;
        if (collider.hasAll(AsteroidTag.class)) return;
        if (collider.hasAll(CrystalTag.class)) return;

        // If collider owner is enemy, do nothing
        var ownership = collider.get(Ownership.class);
        if (ownership != null && ownership.owner().hasAll(EnemyTag.class)) {
            return;
        }

        // Mark enemy to be removed
        enemy.removed(true);
    }

    @Override
    public void start(IWorld world) {

    }
}
