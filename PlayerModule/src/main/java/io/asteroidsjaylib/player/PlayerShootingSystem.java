package io.asteroidsjaylib.player;

import io.asteroidsjaylib.bullet.BulletEntity;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.KeyReleasedEvent;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.physics.component.*;
import io.asteroidsjaylib.spawn.SpawnEvent;

import java.util.List;

import static com.raylib.Raylib.KEY_SPACE;

public class PlayerShootingSystem extends IteratingSystem {

    private double timeSinceLastShot = 0;
    private final double shootInterval = 0.2;
    private boolean isShooting = false;

    @Override
    public void start(World world) {
        this.setPriority(10);
        this.timeSinceLastShot = shootInterval;
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::keyReleased);
    }

    private void keyPressed(World world, KeyPressedEvent event){
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        switch (event.keyCode){
            case KEY_SPACE:
                this.isShooting = true;
                break;
        }
    }

    private void keyReleased(World world, KeyReleasedEvent event){
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        switch (event.keyCode){
            case KEY_SPACE:
                this.isShooting = false;
                break;
        }
    }

    @Override
    public void processEntity(World world, BaseEntity player, double deltaTime) {
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

    private void shoot(World world, BaseEntity player) {
        PositionComponent position = player.getComponent(PositionComponent.class);
        AngleComponent angle = player.getComponent(AngleComponent.class);

        Vector startPosition = position.pos.copy().add(Vector.fromAngle(angle.angle).setMag(60));
        Vector velocity = Vector.fromAngle(angle.angle).setMag(600);

        BulletEntity bullet = new BulletEntity(player, startPosition, velocity);

        SpawnEvent event = new SpawnEvent();
        event.entityToSpawn = bullet;
        world.getEventBus().publish(world, event);
    }
}
