package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics.AccelerationComponent;
import io.asteroidsjaylib.common.physics.AngleComponent;
import io.asteroidsjaylib.common.physics.RotationComponent;
import io.asteroidsjaylib.common.render.AnimationImageComponent;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.player.PlayerTag;

import static com.raylib.Raylib.*;

import java.util.List;

public class PlayerMovementSystem extends IteratingSystem {

    private boolean accelerating = false;
    private float animationTimer = 0;
    private float nextInterval = 0.07f; // Start with a default speed
    private int currentThrustFrame = 1;

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
                player.getComponent(RotationComponent.class).orElseThrow().dAngle += (float) -90;
                break;
            case KEY_RIGHT, KEY_D:
                player.getComponent(RotationComponent.class).orElseThrow().dAngle += (float) 90;
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
                player.getComponent(RotationComponent.class).orElseThrow().dAngle += (float) 90;
                break;
            case KEY_RIGHT, KEY_D:
                player.getComponent(RotationComponent.class).orElseThrow().dAngle += (float) -90;
                break;
            case KEY_UP, KEY_W:
                accelerating = false;
                break;
        }
    }

    @Override
    public void processEntity(IWorld world, BaseEntity player, float deltaTime) {

        AnimationImageComponent img = player.getComponent(RenderTag.class).orElseThrow().getRenderComponent(AnimationImageComponent.class);

        if(!accelerating) {
            img.frameIndex = 0;
            animationTimer = 0;
            return;
        }


        // 1. Add deltaTime to our accumulator
        animationTimer += deltaTime;

        // 2. The "Trick": Only change frames when the timer exceeds our threshold
        if (animationTimer >= nextInterval) {
            animationTimer = 0; // Reset the accumulator

            // 3. Pick a "random but not excessive" next step
            // We ensure the new frame is different from the current one for a clear flicker
            int nextFrame;
            do {
                nextFrame = (int) (Math.random() * 3) + 1; // Pick 1, 2, or 3
            } while (nextFrame == currentThrustFrame);

            currentThrustFrame = nextFrame;
            img.frameIndex = currentThrustFrame;

            // 4. Randomize the NEXT interval slightly to break the mechanical pattern
            // This creates the "organic" feel
            nextInterval = 0.04f + (float)(Math.random() * 0.06f);
        }


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
