package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.collision.SphereCollider;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Rotation;
import io.asteroidsjaylib.common.physics3d.Velocity;
import io.asteroidsjaylib.common.render.Render3D;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.Model3D;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public class EnemyEntity extends BaseEntity {

    public EnemyEntity(Vector3D startPosition){

        this.add(new EnemyTag());

        this.add(new Position().vector(startPosition));

        Rotation rotationComponent = new Rotation().quaternion(Quaternion.randomQuaternion());
        this.add(rotationComponent);

        Vector3D velocity = rotationComponent.quaternion().rotateVector(new Vector3D(500, 0, 0));
        this.add(new Velocity().vector(velocity));

        Render3D render3D = new Render3D();

        Model3D tieBody = new Model3D("/LegoTIEBodyOptimizedLOD.glb", 1, 90, -90, 0);
        tieBody.lodCount = 3;
        tieBody.applyShader(ShaderManager.getShader("solid"));
        render3D.addShape(tieBody);

        Model3D tieWindscreen = new Model3D("/LegoTIEWindshieldOptimized.glb", 1, 90, -90, 0);
        tieWindscreen.applyShader(ShaderManager.getShader("glass"));
        render3D.addShape(tieWindscreen);

        this.add(render3D);

        this.add(new SphereCollider().radius(80));

    }

}
