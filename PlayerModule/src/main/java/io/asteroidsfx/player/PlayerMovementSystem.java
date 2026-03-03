package io.asteroidsfx.player;

import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.IteratingSystem;
import io.asteroidsfx.common.event.input.KeyPressedEvent;
import io.asteroidsfx.common.event.input.KeyReleasedEvent;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.physics.component.AccelerationComponent;
import io.asteroidsfx.physics.component.AngleComponent;
import io.asteroidsfx.physics.component.RotationComponent;

import java.util.List;

public class PlayerMovementSystem extends IteratingSystem {

    private boolean accelerating = false;

    @Override
    public void start(World world) {
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::keyReleased);
    }

    private void keyPressed(World world, KeyPressedEvent event) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case LEFT, A:
                player.getComponent(RotationComponent.class).dAngle = -Math.PI;
                break;
            case RIGHT, D:
                player.getComponent(RotationComponent.class).dAngle = Math.PI;
                break;
            case UP, W:
                accelerating = true;
                break;
        }
    }

    private void keyReleased(World world, KeyReleasedEvent event) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case LEFT, A:
            case RIGHT, D:
                player.getComponent(RotationComponent.class).dAngle = 0;
                break;
            case UP, W:
                accelerating = false;
        }
    }

    @Override
    public void processEntity(World world, BaseEntity player, double deltaTime) {
        if(!accelerating) return;

        AccelerationComponent acceleration = player.getComponent(AccelerationComponent.class);
        double angle = player.getComponent(AngleComponent.class).angle;
        int force = 2500;
        acceleration.acc.add(Vector.fromAngle(angle).setMag(force));
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PlayerTag.class);
    }

}
