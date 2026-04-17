package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IntervalIteratingSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;
import java.util.ServiceLoader;

public class EnemySystem extends IntervalIteratingSystem {

    private final Vector3D VECTOR3D_SCRATCHPAD = new Vector3D();
    private BulletSPI bulletSPI;

    @Override
    public void start(IWorld world) {
        this.setPriority(30);
        this.interval = 2.0;
        bulletSPI = ServiceLoader.load(BulletSPI.class).findFirst().orElse(null);
    }

    @Override
    public void updateInterval(IWorld world, BaseEntity enemy, double deltaTime) {

        if (bulletSPI == null) return;
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();

        PositionComponent enemyPosition = enemy.getComponent(PositionComponent.class);
        PositionComponent playerPosition = player.getComponent(PositionComponent.class);

        if(Vector3D.dist(enemyPosition.pos, playerPosition.pos) > 2500) return;

        Vector3D bulletStart = enemyPosition.pos.copy();
        Vector3D direction = playerPosition.pos.copy().sub(enemyPosition.pos).normalize();
        Vector3D bulletVelocity = direction.copy().setMag(1250);

        Vector3D defaultForward = VECTOR3D_SCRATCHPAD.set(1, 0, 0);
        Quaternion aimRotation = Quaternion.fromToRotation(defaultForward, direction);

        world.getEventBus().publish(world, new SpawnEvent(bulletSPI.CreateBullet(enemy, bulletStart, bulletVelocity, aimRotation)));

    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(EnemyTag.class);
    }

}
