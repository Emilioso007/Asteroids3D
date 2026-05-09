package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics3d.Acceleration;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Rotation;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.LightManager;
import io.asteroidsjaylib.common.render.Render3D;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;
import org.springframework.context.event.EventListener;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.KeyboardKey.*;

import java.util.List;

public class PlayerMovementSystem extends IteratingSystem {

    private boolean accelerating = false;
    private boolean yawLeft = false, yawRight = false;
    private boolean pitchUp = false, pitchDown = false;
    private boolean rollLeft = false, rollRight = false;

    private final float maxTurnSpeed = (float) Math.toRadians(90);
    private final float angularAcceleration = (float) Math.toRadians(270);
    private final float angularDrag = (float) Math.toRadians(180);

    private float currentYawSpeed = 0f;
    private float currentPitchSpeed = 0f;
    private float currentRollSpeed = 0f;

    @Override
    public void start(IWorld world) {
        this.priority(5);
    }

    @EventListener
    void keyPressed(KeyPressedEvent event) {
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

    @EventListener
    private void keyReleased(KeyReleasedEvent event) {
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
    public void update(IWorld world, BaseEntity player, float deltaTime) {

        Rotation playerRotation = player.get(Rotation.class);
        assert playerRotation != null;

        currentYawSpeed = updateSpeed(currentYawSpeed, yawLeft, yawRight, deltaTime);
        currentPitchSpeed = updateSpeed(currentPitchSpeed, pitchDown, pitchUp, deltaTime);
        currentRollSpeed = updateSpeed(currentRollSpeed, rollRight, rollLeft, deltaTime);

        float yawAmount = currentYawSpeed * deltaTime;
        float pitchAmount = currentPitchSpeed * deltaTime;
        float rollAmount = currentRollSpeed * deltaTime;

        if (yawAmount != 0){
            Quaternion yaw = Quaternion.fromAxisAngle(new Vector3D(0, 0, 1), yawAmount);
            playerRotation.quaternion().multiply(yaw).normalize();
        }
        if (pitchAmount != 0){
            Quaternion pitch = Quaternion.fromAxisAngle(new Vector3D(0, 1, 0), pitchAmount);
            playerRotation.quaternion().multiply(pitch).normalize();
        }
        if (rollAmount != 0){
            Quaternion roll = Quaternion.fromAxisAngle(new Vector3D(1, 0, 0), rollAmount);
            playerRotation.quaternion().multiply(roll).normalize();
        }

        Render3D render3D = player.get(Render3D.class);
        assert render3D != null;

        if(accelerating) {
            render3D.currentState("thrust");

            Acceleration playerAcceleration = player.get(Acceleration.class);
            Position playerPosition = player.get(Position.class);
            assert playerAcceleration != null;
            assert playerPosition != null;

            Vector3D acceleration = playerAcceleration.vector();
            Quaternion heading = playerRotation.quaternion();
            Vector3D forceVector = new Vector3D(1500, 0, 0);
            acceleration.add(heading.rotateVector(forceVector));

            Vector3D playerPos = playerPosition.vector();

            Vector3D centerBackLocal = new Vector3D(-50f, 0, 0);
            Vector3D lightPos = playerPos.copy().add(heading.rotateVector(centerBackLocal));

            float pulse = (float) (Math.sin(getTime() * 45) * 0.2 + 1.0);
            float intensity = 6.0f;

            float red   = 0.99f * pulse * intensity;
            float green = 0.56f * pulse * intensity;
            float blue  = 0.81f * pulse * intensity;

            LightManager.addLightSource(lightPos.x, lightPos.y, lightPos.z, red, green, blue);
        } else {
            render3D.currentState("normal");
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
        return Math.clamp(currentSpeed, -maxTurnSpeed, maxTurnSpeed);
    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(PlayerTag.class, Position.class, Acceleration.class, Rotation.class, Render3D.class);
    }

}
