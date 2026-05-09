package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.ownership.Ownership;
import io.asteroidsjaylib.common.physics3d.Acceleration;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class BulletSeekingSystem extends IteratingSystem {

    private static final Vector3D VECTOR_3D_SCRATCHPAD = new Vector3D();
    private static final float MAX_RANGE_SQ = 250.0f * 250.0f; // 62500
    private static final float MIN_RANGE_SQ = 1.0f * 1.0f;     // 1

    @Override
    public void start(IWorld world) {
        this.priority(10);
    }

    @Override
    public void update(IWorld world, BaseEntity bullet, float deltaTime) {

        // Only player bullets can seek!
        Ownership ownership = bullet.get(Ownership.class);
        if(ownership != null && ownership.owner().hasNone(PlayerTag.class)) {
            return;
        }

        Position bulletPosition = bullet.get(Position.class);
        assert bulletPosition != null;

        List<BaseEntity> asteroids = world.getEntitiesWith(AsteroidTag.class, Position.class);
        List<BaseEntity> enemies = world.getEntitiesWith(EnemyTag.class, Position.class);

        BaseEntity closestTarget = null;
        float closestDistSq = MAX_RANGE_SQ;

        // Check Asteroids
        for (BaseEntity asteroid : asteroids) {
            Position asteroidPosition = asteroid.get(Position.class);
            assert asteroidPosition != null;

            float distSq = Vector3D.distanceSquared(bulletPosition.vector(), asteroidPosition.vector());

            if (distSq < closestDistSq && distSq > MIN_RANGE_SQ) {
                closestDistSq = distSq;
                closestTarget = asteroid;
            }
        }

        // Check Enemies
        for (BaseEntity enemy : enemies) {
            Position enemyPosition = enemy.get(Position.class);
            assert enemyPosition != null;

            float distSq = Vector3D.distanceSquared(bulletPosition.vector(), enemyPosition.vector());

            if (distSq < closestDistSq && distSq > MIN_RANGE_SQ) {
                closestDistSq = distSq;
                closestTarget = enemy;
            }
        }

        // Apply force towards the closest target
        if (closestTarget != null) {
            Position targetPosition = closestTarget.get(Position.class);
            assert targetPosition != null;

            float dx = targetPosition.vector().x - bulletPosition.vector().x;
            float dy = targetPosition.vector().y - bulletPosition.vector().y;
            float dz = targetPosition.vector().z - bulletPosition.vector().z;

            VECTOR_3D_SCRATCHPAD.set(dx, dy, dz).normalize().multiply(100000);
            Acceleration acceleration = bullet.get(Acceleration.class);
            assert acceleration != null;
            acceleration.vector().add(VECTOR_3D_SCRATCHPAD);
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(BulletTag.class, Position.class, Acceleration.class);
    }
}