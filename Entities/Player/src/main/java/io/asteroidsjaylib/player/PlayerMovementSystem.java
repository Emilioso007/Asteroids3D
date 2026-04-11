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
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

import static com.raylib.Raylib.*;

import java.util.List;

public class PlayerMovementSystem extends IteratingSystem {

    private boolean accelerating = false;
    private boolean yawLeft = false, yawRight = false;
    private boolean pitchUp = false, pitchDown = false;
    private boolean rollLeft = false, rollRight = false;

    private float turnSpeed = (float) Math.toRadians(180);

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
            case KEY_A:
                yawLeft = true;
                break;
            case KEY_D:
                yawRight = true;
                break;
            case KEY_Q:
                rollLeft = true;
                break;
            case KEY_E:
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
            case KEY_A:
                yawLeft = false;
                break;
            case KEY_D:
                yawRight = false;
                break;
            case KEY_Q:
                rollLeft = false;
                break;
            case KEY_E:
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

        float yawAmount = 0, pitchAmount = 0, rollAmount = 0;
        if (yawLeft) yawAmount += turnSpeed * deltaTime;
        if (yawRight) yawAmount -= turnSpeed * deltaTime;
        if (pitchUp) pitchAmount -= turnSpeed * deltaTime;
        if (pitchDown) pitchAmount += turnSpeed * deltaTime;
        if (rollLeft) rollAmount -= turnSpeed * deltaTime;
        if (rollRight) rollAmount += turnSpeed * deltaTime;

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
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PlayerTag.class);
    }

}
