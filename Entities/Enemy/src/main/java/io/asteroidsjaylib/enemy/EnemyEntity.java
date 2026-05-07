package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.Model3D;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public class EnemyEntity extends BaseEntity {

    public EnemyEntity(Vector3D startPosition){

        this.addComponent(new EnemyTag());

        this.addComponent(new PositionComponent(startPosition));

        RotationComponent rotationComponent = new RotationComponent(Quaternion.randomQuaternion());
        this.addComponent(rotationComponent);

        this.addComponent(new VelocityComponent(rotationComponent.quaternion.rotateVector(new Vector3D(500, 0, 0))));

        Render3DComponent render3DComponent = new Render3DComponent();

        Model3D tieBody = new Model3D("/LegoTIEBodyOptimized.glb", 1, 90, -90, 0);
        tieBody.applyShader(ShaderManager.getShader("solid"));
        render3DComponent.addShape(tieBody);

        Model3D tieWindscreen = new Model3D("/LegoTIEWindshieldOptimized.glb", 1, 90, -90, 0);
        tieWindscreen.applyShader(ShaderManager.getShader("glass"));
        render3DComponent.addShape(tieWindscreen);

        this.addComponent(render3DComponent);

        this.addComponent(new SphereColliderComponent(80));

    }

}
