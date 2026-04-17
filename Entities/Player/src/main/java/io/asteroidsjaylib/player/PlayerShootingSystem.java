package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;
import java.util.ServiceLoader;

import static com.raylib.Raylib.KEY_F;

public class PlayerShootingSystem extends IteratingSystem {

    private static final float SHOT_INTERVAL_SECONDS = 0.2f;
    private boolean firing = false;
    private float cooldownSeconds = 0f;
    private BulletSPI bulletSPI;

    @Override
    public void start(IWorld world) {
        this.setPriority(12);
        bulletSPI = ServiceLoader.load(BulletSPI.class).findFirst().orElse(null);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::keyReleased);
    }

    private void keyPressed(IWorld world, KeyPressedEvent event) {
        if (event.keyCode == KEY_F) firing = true;
    }

    private void keyReleased(IWorld world, KeyReleasedEvent event) {
        if (event.keyCode == KEY_F) firing = false;
    }

    @Override
    public void processEntity(IWorld world, BaseEntity player, float deltaTime) {
        if (cooldownSeconds > 0f) {
            cooldownSeconds = Math.max(0f, cooldownSeconds - deltaTime);
        }

        if (!firing || cooldownSeconds > 0f) return;

        shoot(world, player);
        cooldownSeconds = SHOT_INTERVAL_SECONDS;
    }

    private void shoot(IWorld world, BaseEntity player) {

        if(bulletSPI == null) return;

        Vector3D playerPos = player.getComponent(PositionComponent.class).pos;
        Vector3D playerVel = player.getComponent(VelocityComponent.class).vel;
        Quaternion playerRot = player.getComponent(RotationComponent.class).quaternion;

        Vector3D forwardVector = playerRot.rotateVector(new Vector3D(1, 0, 0));

        Vector3D nosePosition = playerPos.copy().add(forwardVector.copy().mult(50));

        Vector3D bulletVelocity = playerVel.copy().add(forwardVector.copy().mult(2500));

        world.getEventBus().publish(world, new SpawnEvent(bulletSPI.CreateBullet(player, nosePosition, bulletVelocity, playerRot)));
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PlayerTag.class, PositionComponent.class, VelocityComponent.class, RotationComponent.class);
    }
}
