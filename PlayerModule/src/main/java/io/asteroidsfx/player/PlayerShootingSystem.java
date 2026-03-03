package io.asteroidsfx.player;

import io.asteroidsfx.bullet.BulletEntity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.IteratingSystem;
import io.asteroidsfx.common.event.input.KeyPressedEvent;
import io.asteroidsfx.common.event.input.KeyReleasedEvent;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.physics.component.*;
import io.asteroidsfx.spawn.SpawnEvent;

import java.util.List;

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
            case SPACE:
                this.isShooting = true;
                break;
        }
    }

    private void keyReleased(World world, KeyReleasedEvent event){
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        switch (event.keyCode){
            case SPACE:
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
