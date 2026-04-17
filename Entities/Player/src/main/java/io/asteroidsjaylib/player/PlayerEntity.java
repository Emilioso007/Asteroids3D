package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.*;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.Model3D;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class PlayerEntity extends BaseEntity {

    public PlayerEntity(Vector3D startPosition){

        this.addComponent(new PlayerTag());

        this.addComponent(new PositionComponent(startPosition));

        this.addComponent(new VelocityComponent());

        this.addComponent(new AccelerationComponent());

        this.addComponent(new RotationComponent());

        this.addComponent(new DragComponent(0.25f));

        Render3DComponent render3DComponent = new Render3DComponent();
        Model3D normal = new Model3D("/LegoSpaceship.glb", 1, 90,-90,0);
        normal.applyShader(ShaderManager.getShader("solid"));
        render3DComponent.addShape(normal, List.of("normal", "thrust"));
        Model3D thrustModel = new Model3D("/LegoFlame.glb", 1, 90,-90,0);
        thrustModel.applyShader(ShaderManager.getShader("thruster"));
        render3DComponent.addShape(thrustModel, "thrust");
        render3DComponent.setCurrentState("normal");
        this.addComponent(render3DComponent);

        this.addComponent(new SphereColliderComponent(40));

    }

}