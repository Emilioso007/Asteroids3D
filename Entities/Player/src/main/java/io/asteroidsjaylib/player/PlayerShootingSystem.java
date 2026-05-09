package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Rotation;
import io.asteroidsjaylib.common.physics3d.Velocity;
import io.asteroidsjaylib.common.util.ITimeProvider;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.ServiceLoader;

import static com.raylib.Raylib.KeyboardKey.KEY_F;

public class PlayerShootingSystem extends IteratingSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ITimeProvider timeProvider;

    private static final float SHOT_INTERVAL_SECONDS = 0.2f;
    private boolean firing = false;
    private float cooldownSeconds = 0f;
    private BulletSPI bulletSPI;

    private int currentBarrel = 0;
    private Vector3D[] barrelOffsets;

    @Override
    public void start(IWorld world) {
        this.priority(12);
        bulletSPI = ServiceLoader.load(BulletSPI.class).findFirst().orElse(null);

        float forwardBarrelOffset = 100f;
        float leftBarrelOffset = 82.5f;
        float rightBarrelOffset = -82.5f;
        float topBarrelOffset = 20f;
        float bottomBarrelOffset = -47.5f;

        barrelOffsets = new Vector3D[]{
                new Vector3D(forwardBarrelOffset, leftBarrelOffset, topBarrelOffset), // Top Left
                new Vector3D(forwardBarrelOffset, rightBarrelOffset, bottomBarrelOffset), // Bottom Right
                new Vector3D(forwardBarrelOffset, rightBarrelOffset, topBarrelOffset), // Top Right
                new Vector3D(forwardBarrelOffset, leftBarrelOffset, bottomBarrelOffset)  // Bottom Left
        };
    }

    @EventListener
    private void keyPressed(KeyPressedEvent event) {
        if (event.keyCode == KEY_F) firing = true;
    }

    @EventListener
    private void keyReleased(KeyReleasedEvent event) {
        if (event.keyCode == KEY_F) firing = false;
    }

    @Override
    public void update(IWorld world, BaseEntity player, float deltaTime) {
        if (cooldownSeconds > 0f) {
            cooldownSeconds = Math.max(0f, cooldownSeconds - deltaTime);
        }

        if (!firing || cooldownSeconds > 0f) return;

        shoot(player);
        cooldownSeconds = SHOT_INTERVAL_SECONDS;
    }

    private void shoot(BaseEntity player) {
        if (bulletSPI == null) return;

        Position position = player.get(Position.class);
        Velocity velocity = player.get(Velocity.class);
        Rotation rotation = player.get(Rotation.class);
        assert position != null;
        assert velocity != null;
        assert rotation != null;

        Vector3D rotatedOffset = rotation.quaternion().rotateVector(barrelOffsets[currentBarrel].copy());
        Vector3D worldMuzzlePos = position.vector().copy().add(rotatedOffset);

        Vector3D forwardVector = rotation.quaternion().rotateVector(new Vector3D(1, 0, 0));
        Vector3D bulletVelocity = velocity.vector().copy().add(forwardVector.copy().multiply(2000));

        eventPublisher.publishEvent(new SpawnEvent(bulletSPI.CreateBullet(
                player,
                worldMuzzlePos,
                bulletVelocity,
                rotation.quaternion().copy(),
                timeProvider.getTime()
        )));

        currentBarrel = (currentBarrel + 1) % 4;
    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(PlayerTag.class, Position.class, Velocity.class, Rotation.class);
    }
}
