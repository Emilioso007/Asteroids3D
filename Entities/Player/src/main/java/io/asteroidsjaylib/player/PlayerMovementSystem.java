package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics.AccelerationComponent;
import io.asteroidsjaylib.common.physics.AngleComponent;
import io.asteroidsjaylib.common.physics.RotationComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.player.PlayerTag;

import static com.raylib.Raylib.*;

import java.util.List;

public class PlayerMovementSystem extends IteratingSystem {

    private boolean accelerating = false;

    @Override
    public void start(IWorld world) {
        this.setPriority(5);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::keyReleased);
    }

    private void keyPressed(IWorld world, KeyPressedEvent event) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case KEY_LEFT, KEY_A:
                player.getComponent(RotationComponent.class).orElseThrow().dAngle = (float) -90;
                break;
            case KEY_RIGHT, KEY_D:
                player.getComponent(RotationComponent.class).orElseThrow().dAngle = (float) 90;
                break;
            case KEY_UP, KEY_W:
                accelerating = true;
                break;
        }
    }

    private void keyReleased(IWorld world, KeyReleasedEvent event) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case KEY_LEFT, KEY_A:
            case KEY_RIGHT, KEY_D:
                player.getComponent(RotationComponent.class).orElseThrow().dAngle = 0;
                break;
            case KEY_UP, KEY_W:
                accelerating = false;
        }
    }

    @Override
    public void processEntity(IWorld world, BaseEntity player, float deltaTime) {
        if(!accelerating) return;

        Vector2D acceleration = player.getComponent(AccelerationComponent.class).orElseThrow().acc;
        float angle = player.getComponent(AngleComponent.class).orElseThrow().angle;
        int force = 2500;
        acceleration.add(Vector2D.fromAngle(angle).setMag(force));
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PlayerTag.class);
    }

}
