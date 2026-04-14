package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.common.event.EventSubscription;
import io.asteroidsjaylib.common.event.input.key.KeyDownEvent;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Vector3D;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.ServiceLoader;

import static com.raylib.Raylib.KEY_F;

public class PlayerShootingSystem implements EventSubscriberSPI {

    private Instant lastShot = Instant.EPOCH;
    private final Duration timeBetweenShots = Duration.ofMillis(200);

    @Override
    public List<EventSubscription<? extends BaseEvent>> getEventSubscriptions() {
        return List.of(new EventSubscription<>(KeyDownEvent.class, this::keyDown));
    }

    private void keyDown(IWorld world, KeyDownEvent keyDownEvent) {

        if (keyDownEvent.keyCode != KEY_F) return;

        if (!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();

        Instant now = Instant.now();

        if (now.isAfter(lastShot.plus(timeBetweenShots))) {
            shoot(world, player);
            lastShot = now;
        }
    }

    private void shoot(IWorld world, BaseEntity player) {

        Vector3D playerPos = player.getComponent(PositionComponent.class).pos;
        Vector3D playerVel = player.getComponent(VelocityComponent.class).vel;
        Quaternion playerRot = player.getComponent(RotationComponent.class).quaternion;

        Vector3D forwardVector = playerRot.rotateVector(new Vector3D(1, 0, 0));

        Vector3D nosePosition = playerPos.copy().add(forwardVector.copy().mult(50));

        Vector3D bulletVelocity = playerVel.copy().add(forwardVector.copy().mult(2500));

        BulletSPI bulletSPI = ServiceLoader.load(BulletSPI.class).findFirst().orElseThrow();
        world.getEventBus().publish(world, new SpawnEvent(bulletSPI.CreateBullet(player, nosePosition, bulletVelocity, playerRot)));
    }
}
