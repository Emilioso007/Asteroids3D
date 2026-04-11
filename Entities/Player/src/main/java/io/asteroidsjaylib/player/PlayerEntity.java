package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.*;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.shapes3d.Cube3D;
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

        Render3DComponent render3DComponent = new Render3DComponent();
        Cube3D cube3D = new Cube3D(100, 40, 40, RED, WHITE);
        render3DComponent.shapes.add(cube3D);
        this.addComponent(render3DComponent);

        SphereColliderComponent sphereColliderComponent = new SphereColliderComponent(60);
        this.addComponent(sphereColliderComponent);

    }

}