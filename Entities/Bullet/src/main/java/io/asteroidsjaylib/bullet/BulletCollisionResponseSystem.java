package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.ownership.Ownership;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import org.springframework.context.event.EventListener;

public class BulletCollisionResponseSystem extends ResponseSystem {

    @EventListener
    private void handleCollision(CollisionEvent event) {
        // If no bullet in collision, do nothing
        if(!event.hasEntityWith(BulletTag.class)) return;

        BaseEntity bullet = event.getEntityWith(BulletTag.class);
        BaseEntity collider = event.getOther(bullet);

        // If collider is also bullet, do nothing
        if (collider.hasAll(BulletTag.class)) return;
        if (collider.hasAll(CrystalTag.class)) return;

        // If collider is also bullet owner, do nothing
        Ownership ownership = bullet.get(Ownership.class);
        if (ownership != null && ownership.owner() == collider) return;

        // Mark bullet to be removed
        bullet.removed(true);
    }

    @Override
    public void start(IWorld world) {

    }
}
