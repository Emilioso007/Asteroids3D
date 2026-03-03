package io.asteroidsfx.bullet;

import io.asteroidsfx.ownership.OwnershipComponent;
import io.asteroidsfx.collision.CollisionEvent;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.ResponseSystem;

public class BulletCollisionResponseSystem extends ResponseSystem {
    @Override
    public void start(World world) {
        world.getEventBus().subscribe(CollisionEvent.class, this::handleCollision);
    }

    private void handleCollision(World world, CollisionEvent event) {
        // If no bullet in collision, do nothing
        if(!event.hasEntityWith(BulletTag.class)) return;

        BaseEntity bullet = event.getEntityWith(BulletTag.class);
        BaseEntity collider = event.getOther(bullet);

        // If collider is also bullet, do nothing
        if (collider.hasComponent(BulletTag.class)) return;

        // If collider is also bullet owner, do nothing
        if (bullet.getComponent(OwnershipComponent.class).owner == collider) return;

        // Mark bullet to be removed
        bullet.setToBeRemoved(true);
    }
}
