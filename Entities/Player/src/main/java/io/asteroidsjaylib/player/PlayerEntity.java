package io.asteroidsjaylib.player;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.*;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.shapes3d.Model3D;
import io.asteroidsjaylib.common.util.Vector3D;

public class PlayerEntity extends BaseEntity {

    public PlayerEntity(Vector3D startPosition, Raylib.Shader shader){

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
        /*
        Cube3D cube3D = new Cube3D(100, 40, 40, RED, WHITE);
        render3DComponent.shapes.add(cube3D);
        */
        /*
        Sphere3D sphere3D = new Sphere3D(40, BLANK, WHITE);
        render3DComponent.shapes.add(sphere3D);
        */

        // Model3D model3D = new Model3D("/craft_speederA.obj", "/craft_speederA.mtl", 40, 80,90,0);

        Model3D idleModel = new Model3D("/LegoSpaceship.obj", "/LegoSpaceship.mtl", 1, 90,-90,0);
        idleModel.applyShader(shader);
        render3DComponent.shapeLibrary.put("idle", idleModel);
        Model3D thrustModel = new Model3D("/LegoSpaceshipThrust.obj", "/LegoSpaceshipThrust.mtl", 1, 90,-90,0);
        thrustModel.applyShader(shader);
        render3DComponent.shapeLibrary.put("thrust", thrustModel);
        render3DComponent.state = "idle";
        this.addComponent(render3DComponent);

        SphereColliderComponent sphereColliderComponent = new SphereColliderComponent(40);
        this.addComponent(sphereColliderComponent);

    }

}