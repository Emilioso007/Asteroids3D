package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IntervalIteratingSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.ITimeProvider;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.ServiceLoader;

public class EnemySystem extends IntervalIteratingSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ITimeProvider timeProvider;

    private final Vector3D VECTOR3D_SCRATCHPAD = new Vector3D();
    private BulletSPI bulletSPI;

    @Override
    public void start(IWorld world) {
        this.priority(30);
        this.interval = 2.0;
        bulletSPI = ServiceLoader.load(BulletSPI.class).findFirst().orElse(null);
    }

    @Override
    public void update(IWorld world, BaseEntity enemy, double deltaTime) {

        if (bulletSPI == null) return;
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();

        Position enemyPosition = enemy.get(Position.class);
        Position playerPosition = player.get(Position.class);
        assert enemyPosition != null;
        assert playerPosition != null;

        if(Vector3D.distance(enemyPosition.vector(), playerPosition.vector()) > 2500) return;

        Vector3D bulletStart = enemyPosition.vector().copy();
        Vector3D direction = playerPosition.vector().copy().subtract(enemyPosition.vector()).normalize();
        Vector3D bulletVelocity = direction.copy().magnitude(1250);

        Vector3D defaultForward = VECTOR3D_SCRATCHPAD.set(1, 0, 0);
        Quaternion aimRotation = Quaternion.fromToRotation(defaultForward, direction);

        eventPublisher.publishEvent(new SpawnEvent(bulletSPI.CreateBullet(enemy, bulletStart, bulletVelocity, aimRotation, timeProvider.getTime())));

    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(EnemyTag.class);
    }

}
