package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics3d.AccelerationComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

import static com.raylib.Raylib.*;

import java.util.List;

public class PlayerMovementSystem extends IteratingSystem {

    private boolean accelerating = false;
    private boolean yawLeft = false, yawRight = false;
    private boolean pitchUp = false, pitchDown = false;
    private boolean rollLeft = false, rollRight = false;

    private float maxTurnSpeed = (float) Math.toRadians(90);
    private float angularAcceleration = (float) Math.toRadians(270);
    private float angularDrag = (float) Math.toRadians(180);

    private float currentYawSpeed = 0f;
    private float currentPitchSpeed = 0f;
    private float currentRollSpeed = 0f;

    @Override
    public void start(IWorld world) {
        this.setPriority(5);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::keyReleased);

    }

    private void keyPressed(IWorld world, KeyPressedEvent event) {
        switch (event.keyCode){
            case KEY_W:
                pitchDown = true;
                break;
            case KEY_S:
                pitchUp = true;
                break;
            case KEY_Q:
                yawLeft = true;
                break;
            case KEY_E:
                yawRight = true;
                break;
            case KEY_A:
                rollLeft = true;
                break;
            case KEY_D:
                rollRight = true;
                break;
            case KEY_SPACE:
                accelerating = true;
                break;
        }
    }

    private void keyReleased(IWorld world, KeyReleasedEvent event) {
        switch (event.keyCode){
            case KEY_W:
                pitchDown = false;
                break;
            case KEY_S:
                pitchUp = false;
                break;
            case KEY_Q:
                yawLeft = false;
                break;
            case KEY_E:
                yawRight = false;
                break;
            case KEY_A:
                rollLeft = false;
                break;
            case KEY_D:
                rollRight = false;
                break;
            case KEY_SPACE:
                accelerating = false;
                break;
        }
    }

    @Override
    public void processEntity(IWorld world, BaseEntity player, float deltaTime) {

        RotationComponent rotComp = player.getComponent(RotationComponent.class).orElseThrow();

        currentYawSpeed = updateSpeed(currentYawSpeed, yawLeft, yawRight, deltaTime);
        currentPitchSpeed = updateSpeed(currentPitchSpeed, pitchDown, pitchUp, deltaTime);
        currentRollSpeed = updateSpeed(currentRollSpeed, rollRight, rollLeft, deltaTime);

        float yawAmount = currentYawSpeed * deltaTime;
        float pitchAmount = currentPitchSpeed * deltaTime;
        float rollAmount = currentRollSpeed * deltaTime;

        if (yawAmount != 0){
            Quaternion yaw = Quaternion.fromAxisAngle(new Vector3D(0, 0, 1), yawAmount);
            rotComp.quaternion.multiply(yaw).normalize();
        }
        if (pitchAmount != 0){
            Quaternion pitch = Quaternion.fromAxisAngle(new Vector3D(0, 1, 0), pitchAmount);
            rotComp.quaternion.multiply(pitch).normalize();
        }
        if (rollAmount != 0){
            Quaternion roll = Quaternion.fromAxisAngle(new Vector3D(1, 0, 0), rollAmount);
            rotComp.quaternion.multiply(roll).normalize();
        }

        if(accelerating) {
            Vector3D acceleration = player.getComponent(AccelerationComponent.class).orElseThrow().acc;
            Quaternion heading = player.getComponent(RotationComponent.class).orElseThrow().quaternion;
            Vector3D forceVector = new Vector3D(2500, 0, 0);
            acceleration.add(heading.rotateVector(forceVector));

            player.getComponent(Render3DComponent.class).orElseThrow().setCurrentState("thrust");
        } else {
            player.getComponent(Render3DComponent.class).orElseThrow().setCurrentState("normal");
        }
    }

    private float updateSpeed(float currentSpeed, boolean positiveInput, boolean negativeInput, float deltaTime) {
        if (positiveInput){
            currentSpeed += angularAcceleration * deltaTime;
        } else if (negativeInput){
            currentSpeed -= angularAcceleration * deltaTime;
        } else {
            if (currentSpeed > 0){
                currentSpeed = Math.max(0, currentSpeed - angularDrag * deltaTime);
            } else if (currentSpeed < 0) {
                currentSpeed = Math.min(0, currentSpeed + angularDrag * deltaTime);
            }
        }
        return Math.max(-maxTurnSpeed, Math.min(maxTurnSpeed, currentSpeed));
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PlayerTag.class);
    }

}
