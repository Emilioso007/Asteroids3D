package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import io.asteroidsjaylib.common.physics3d.AccelerationComponent;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class BulletSeekingSystem extends IteratingSystem {

    private static final Vector3D VECTOR_3D_SCRATCHPAD = new Vector3D();
    private static final float MAX_RANGE_SQ = 250.0f * 250.0f; // 62500
    private static final float MIN_RANGE_SQ = 1.0f * 1.0f;     // 1

    @Override
    public void start(IWorld world) {
        this.setPriority(10);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity bullet, float deltaTime) {

        // Only player bullets can seek!
        if(!bullet.hasComponents(OwnershipComponent.class) || !bullet.getComponent(OwnershipComponent.class).owner.hasComponents(PlayerTag.class)) {
            return;
        }

        Vector3D bulletPosition = bullet.getComponent(PositionComponent.class).pos;

        List<BaseEntity> asteroids = world.getEntitiesWith(AsteroidTag.class, PositionComponent.class);
        List<BaseEntity> enemies = world.getEntitiesWith(EnemyTag.class, PositionComponent.class);

        BaseEntity closestTarget = null;
        float closestDistSq = MAX_RANGE_SQ;

        // Check Asteroids
        for (BaseEntity asteroid : asteroids) {
            Vector3D pos = asteroid.getComponent(PositionComponent.class).pos;
            float distSq = Vector3D.distSq(bulletPosition, pos);

            if (distSq < closestDistSq && distSq > MIN_RANGE_SQ) {
                closestDistSq = distSq;
                closestTarget = asteroid;
            }
        }

        // Check Enemies
        for (BaseEntity enemy : enemies) {
            Vector3D pos = enemy.getComponent(PositionComponent.class).pos;
            float distSq = Vector3D.distSq(bulletPosition, pos);

            if (distSq < closestDistSq && distSq > MIN_RANGE_SQ) {
                closestDistSq = distSq;
                closestTarget = enemy;
            }
        }

        // Apply force towards the closest target
        if (closestTarget != null) {
            Vector3D targetPosition = closestTarget.getComponent(PositionComponent.class).pos;

            float dx = targetPosition.x - bulletPosition.x;
            float dy = targetPosition.y - bulletPosition.y;
            float dz = targetPosition.z - bulletPosition.z;

            VECTOR_3D_SCRATCHPAD.set(dx, dy, dz).normalize().mult(100000);
            bullet.getComponent(AccelerationComponent.class).acc.add(VECTOR_3D_SCRATCHPAD);
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(BulletTag.class, PositionComponent.class, AccelerationComponent.class);
    }
}