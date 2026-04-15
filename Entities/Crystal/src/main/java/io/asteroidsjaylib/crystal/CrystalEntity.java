package io.asteroidsjaylib.crystal;

import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.render.*;
import io.asteroidsjaylib.common.render.shapes3d.Model3D;
import io.asteroidsjaylib.common.util.Vector3D;

public class CrystalEntity extends BaseEntity {

    public CrystalEntity(Vector3D startPosition, int value) {
        this.addComponent(new CrystalTag(value));
        this.addComponent(new PositionComponent(startPosition));
        this.addComponent(new RotationComponent());
        Render3DComponent render3DComponent = new Render3DComponent();
        Model3D crystal = new Model3D("/LegoCrystal.glb", 1, 90, -90, 0);
        crystal.applyShader(ShaderManager.getShader("glass"));
        render3DComponent.addShape(crystal);
        this.addComponent(render3DComponent);

        this.addComponent(new SphereColliderComponent(10));
    }
}