package io.asteroidsfx.enemy;

import io.asteroidsfx.ownership.OwnershipComponent;
import io.asteroidsfx.collision.CollisionEvent;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.ResponseSystem;

public class EnemyCollisionResponseSystem extends ResponseSystem {
    @Override
    public void start(World world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(World world, CollisionEvent event) {
        // If no enemy in collision, do nothing
        if(!event.hasEntityWith(EnemyTag.class)) return;

        BaseEntity enemy = event.getEntityWith(EnemyTag.class);
        BaseEntity collider = event.getOther(enemy);

        // If collider is also enemy, do nothing
        if (collider.hasComponent(EnemyTag.class)) return;

        // If collider owner is enemy, do nothing
        if (collider.hasComponent(OwnershipComponent.class)
                && collider.getComponent(OwnershipComponent.class).owner.hasComponent(EnemyTag.class))
            return;

        // Mark enemy to be removed
        enemy.setToBeRemoved(true);
    }
}
