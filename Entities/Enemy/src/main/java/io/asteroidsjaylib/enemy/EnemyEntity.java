package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.shapes3d.Model3D;
import io.asteroidsjaylib.common.util.Vector3D;

public class EnemyEntity extends BaseEntity {

    public EnemyEntity(Vector3D startPosition){

        this.addComponent(new EnemyTag());

        this.addComponent(new PositionComponent(startPosition));

        this.addComponent(new VelocityComponent(new Vector3D(5, 0, 0)));

        this.addComponent(new RotationComponent());

        Render3DComponent render3DComponent = new Render3DComponent();

        Model3D ufoBody = new Model3D("/LegoUfoBody.glb", 1, 90, -90, 0);
        ufoBody.applyShader(ShaderManager.getShader("solid"));
        render3DComponent.addShape(ufoBody);

        Model3D ufoWindscreen = new Model3D("/LegoUfoWindscreen.glb", 1, 90, -90, 0);
        ufoWindscreen.applyShader(ShaderManager.getShader("glass"));
        render3DComponent.addShape(ufoWindscreen);

        Model3D ufoWeapon = new Model3D("/LegoUfoWeapon.glb", 1, 90, -90, 0);
        ufoWeapon.applyShader(ShaderManager.getShader("glass"));
        render3DComponent.addShape(ufoWeapon);

        this.addComponent(render3DComponent);

        //this.addComponent(new SphereColliderComponent(80));

    }

}
