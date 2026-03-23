package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.event.input.key.KeyDownEvent;
import io.asteroidsjaylib.common.physics.AngleComponent;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.physics.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.ServiceLoader;

import static com.raylib.Raylib.KEY_SPACE;

public class PlayerShootingSystem extends ResponseSystem {

    private Instant lastShot = Instant.EPOCH;
    private Duration timeBetweenShots = Duration.ofMillis(200);

    @Override
    public void start(IWorld world) {
        this.setPriority(10);
        world.getEventBus().subscribe(KeyDownEvent.class, this::keyDown);
    }

    private void keyDown(IWorld world, KeyDownEvent keyDownEvent) {

        if (keyDownEvent.keyCode != KEY_SPACE) return;

        if (!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();

        Instant now = Instant.now();

        if (now.isAfter(lastShot.plus(timeBetweenShots))) {
            shoot(world, player);
            lastShot = now;
        }
    }

    private void shoot(IWorld world, BaseEntity player) {
        PositionComponent position = player.getComponent(PositionComponent.class).orElseThrow();
        VelocityComponent velocityComponent = player.getComponent(VelocityComponent.class).orElseThrow();
        AngleComponent angle = player.getComponent(AngleComponent.class).orElseThrow();

        Vector2D startPosition = position.pos.copy().add(Vector2D.fromAngle(angle.angle).setMag(75/2f));
        Vector2D velocity = Vector2D.fromAngle(angle.angle).setMag(600);
        velocity.add(velocityComponent.vel.copy().setHeading(velocity.heading()));

        BulletSPI bulletSPI = ServiceLoader.load(BulletSPI.class).findFirst().orElseThrow();
        world.getEventBus().publish(world, new SpawnEvent(bulletSPI.CreateBullet(player, startPosition, velocity)));
    }
}
