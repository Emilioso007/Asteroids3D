package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.KeyReleasedEvent;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.physics.component.AccelerationComponent;
import io.asteroidsjaylib.physics.component.AngleComponent;
import io.asteroidsjaylib.physics.component.RotationComponent;

import static com.raylib.Raylib.*;

import java.util.List;

public class PlayerMovementSystem extends IteratingSystem {

    private boolean accelerating = false;

    @Override
    public void start(World world) {
        this.setPriority(5);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::keyReleased);
    }

    private void keyPressed(World world, KeyPressedEvent event) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case KEY_LEFT, KEY_A:
                player.getComponent(RotationComponent.class).dAngle = -Math.PI;
                break;
            case KEY_RIGHT, KEY_D:
                player.getComponent(RotationComponent.class).dAngle = Math.PI;
                break;
            case KEY_UP, KEY_W:
                accelerating = true;
                break;
        }
    }

    private void keyReleased(World world, KeyReleasedEvent event) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case KEY_LEFT, KEY_A:
            case KEY_RIGHT, KEY_D:
                player.getComponent(RotationComponent.class).dAngle = 0;
                break;
            case KEY_UP, KEY_W:
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
