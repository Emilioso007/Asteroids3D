package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.*;
import io.asteroidsjaylib.common.render.shapes3d.Cube3DComponent;
import io.asteroidsjaylib.common.sound.SoundComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.util.Vector3D;

import static com.raylib.Colors.RED;
import static com.raylib.Colors.WHITE;

public class PlayerEntity extends BaseEntity {

    public PlayerEntity(Vector3D startPosition){

        this.addComponent(new PlayerTag());

        PositionComponent positionComponent = new PositionComponent(startPosition);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        this.addComponent(velocityComponent);

        AccelerationComponent accelerationComponent = new AccelerationComponent();
        this.addComponent(accelerationComponent);

        RotationComponent rotationComponent = new RotationComponent();
        this.addComponent(rotationComponent);

        DragComponent dragComponent = new DragComponent();
        dragComponent.drag = 0.25F;
        this.addComponent(dragComponent);

        Cube3DComponent cube3DComponent = new Cube3DComponent(100, 40, 40, RED, WHITE);
        this.addComponent(cube3DComponent);

        SoundComponent soundComponent = new SoundComponent("rocket-sound.wav");
        this.addComponent(soundComponent);

    }

}