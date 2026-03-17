package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;

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
        if (collider.hasComponent(CoinTag.class)) return;

        // If collider is also bullet owner, do nothing
        if (bullet.getComponent(OwnershipComponent.class).orElseThrow().owner == collider) return;

        // Mark bullet to be removed
        bullet.setToBeRemoved(true);
    }
}
