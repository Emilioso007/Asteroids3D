package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics.AngleComponent;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;

import java.util.List;
import java.util.ServiceLoader;

import static com.raylib.Raylib.KEY_SPACE;

public class PlayerShootingSystem extends IteratingSystem {

    private double timeSinceLastShot = 0;
    private final double shootInterval = 0.2;
    private boolean isShooting = false;

    @Override
    public void start(IWorld world) {
        this.setPriority(10);
        this.timeSinceLastShot = shootInterval;
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::keyReleased);
    }

    private void keyPressed(IWorld world, KeyPressedEvent event){
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        switch (event.keyCode){
            case KEY_SPACE:
                this.isShooting = true;
                break;
        }
    }

    private void keyReleased(IWorld world, KeyReleasedEvent event){
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        switch (event.keyCode){
            case KEY_SPACE:
                this.isShooting = false;
                break;
        }
    }

    @Override
    public void processEntity(IWorld world, BaseEntity player, float deltaTime) {
        // Always track time since last shot
        timeSinceLastShot += deltaTime;

        if(!isShooting) return;

        if (timeSinceLastShot >= shootInterval) {
            shoot(world, player);
            timeSinceLastShot = 0;
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PlayerTag.class);
    }

    private void shoot(IWorld world, BaseEntity player) {
        PositionComponent position = player.getComponent(PositionComponent.class).orElseThrow();
        AngleComponent angle = player.getComponent(AngleComponent.class).orElseThrow();

        Vector2D startPosition = position.pos.copy().add(Vector2D.fromAngle(angle.angle).setMag(60));
        Vector2D velocity = Vector2D.fromAngle(angle.angle).setMag(600);

        BulletSPI bulletSPI = ServiceLoader.load(BulletSPI.class).findFirst().orElseThrow();
        world.getEventBus().publish(world, new SpawnEvent(bulletSPI.CreateBullet(player, startPosition, velocity)));

    }
}
