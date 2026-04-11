package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics3d.AccelerationComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.sound.SoundComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

import static com.raylib.Raylib.*;

import java.util.List;

public class PlayerMovementSystem extends IteratingSystem {

    private boolean accelerating = false;
    private boolean turningLeft = false;
    private boolean turningRight = false;

    private float turnSpeed = (float) Math.toRadians(180);

    private float animationTimer = 0;
    private float nextInterval = 0.07f; // Start with a default speed
    private int currentThrustFrame = 1;

    private Vector3D cameraShake;

    @Override
    public void start(IWorld world) {
        this.setPriority(5);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::keyReleased);

        cameraShake = new Vector3D(-5, -5, -5);
    }

    private void keyPressed(IWorld world, KeyPressedEvent event) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case KEY_LEFT, KEY_A:
                turningLeft = true;
                break;
            case KEY_RIGHT, KEY_D:
                turningRight = true;
                break;
            case KEY_UP, KEY_W:
                accelerating = true;
                player.getComponent(SoundComponent.class).orElseThrow().playing = true;
                break;
        }
    }

    private void keyReleased(IWorld world, KeyReleasedEvent event) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case KEY_LEFT, KEY_A:
                turningLeft = false;
                break;
            case KEY_RIGHT, KEY_D:
                turningRight = false;
                break;
            case KEY_UP, KEY_W:
                accelerating = false;
                player.getComponent(SoundComponent.class).orElseThrow().playing = false;
                break;
        }
    }

    @Override
    public void processEntity(IWorld world, BaseEntity player, float deltaTime) {

        /*
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

        world.shakeCamera(cameraShake);

        //cameraShake.rotate(Math.random()*360);

         */



        float turnAmount = 0;
        if (turningLeft) turnAmount += turnSpeed * deltaTime;
        if (turningRight) turnAmount -= turnSpeed * deltaTime;

        if (turnAmount != 0){

            Vector3D rotationAxis = new Vector3D(0, 0, 1);

            Quaternion turn = Quaternion.fromAxisAngle(rotationAxis, turnAmount);

            RotationComponent rotComp = player.getComponent(RotationComponent.class).orElseThrow();

            rotComp.quaternion.multiply(turn).normalize();
        }

        if(accelerating) {
            Vector3D acceleration = player.getComponent(AccelerationComponent.class).orElseThrow().acc;
            Quaternion heading = player.getComponent(RotationComponent.class).orElseThrow().quaternion;
            Vector3D forceVector = new Vector3D(2500, 0, 0);
            acceleration.add(heading.rotateVector(forceVector));
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PlayerTag.class);
    }

}
