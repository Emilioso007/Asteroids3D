package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.collision.SphereCollider;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.*;
import io.asteroidsjaylib.common.render.Render3D;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.Model3D;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class PlayerEntity extends BaseEntity {

    public PlayerEntity(Vector3D startPosition){

        this.add(new PlayerTag());

        this.add(new Position().vector(startPosition));

        this.add(new Velocity());

        this.add(new Acceleration());

        this.add(new Rotation());

        this.add(new Drag().value(0.25f));

        Render3D render3D = new Render3D();

        Model3D body = new Model3D("/LegoXWingBodyOptimized.glb", 1, 90,-90,0);
        body.applyShader(ShaderManager.getShader("solid"));
        render3D.addShape(body, List.of("normal", "thrust"));

        Model3D windshield = new Model3D("/LegoXWingWindshield.glb", 1, 90, -90, 0);
        windshield.applyShader(ShaderManager.getShader("glass"));
        render3D.addShape(windshield, List.of("normal", "thrust"));

        Model3D thruster = new Model3D("/LegoXWingThruster.glb", 1, 90,-90,0);
        thruster.applyShader(ShaderManager.getShader("thruster"));
        render3D.addShape(thruster, "thrust");

        render3D.currentState("normal");
        this.add(render3D);

        this.add(new SphereCollider().radius(40));

    }

}