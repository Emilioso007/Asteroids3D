package io.asteroidsjaylib.crystal;

import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.SphereCollider;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Rotation;
import io.asteroidsjaylib.common.render.*;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public class CrystalEntity extends BaseEntity {

    public CrystalEntity(Vector3D startPosition, Quaternion rotation) {
        this.add(new CrystalTag());
        this.add(new Position().vector(startPosition));
        this.add(new Rotation().quaternion(rotation));
        Render3D render3D = new Render3D();
        Model3D crystal = new Model3D("/LegoCrystal.glb", 1, 90, -90, 0);
        crystal.applyShader(ShaderManager.getShader("glass"));
        render3D.addShape(crystal);
        this.add(render3D);

        this.add(new SphereCollider().radius(10));
    }
}