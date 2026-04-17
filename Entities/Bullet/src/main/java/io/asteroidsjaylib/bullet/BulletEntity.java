package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.lifetime.LifetimeComponent;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.AccelerationComponent;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.Model3D;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

import java.time.Duration;

public class BulletEntity extends BaseEntity{

    public BulletEntity(BaseEntity owner, Vector3D startPosition, Vector3D velocity, Quaternion rotation) {

        this.addComponent(new OwnershipComponent(owner));

        this.addComponent(new BulletTag());

        this.addComponent(new PositionComponent(startPosition));

        VelocityComponent velocityComponent = new VelocityComponent(velocity);
        velocityComponent.terminalVelocity = 2500;
        this.addComponent(velocityComponent);

        this.addComponent(new AccelerationComponent());

        this.addComponent(new RotationComponent(rotation));

        this.addComponent(new LifetimeComponent(Duration.ofSeconds(2)));

        Render3DComponent render3DComponent = new Render3DComponent();
        Model3D laser = new Model3D("/LegoBullet.glb", 1, 90, -90, 0);
        laser.applyShader(ShaderManager.getShader("solid"));
        render3DComponent.addShape(laser);
        this.addComponent(render3DComponent);

        this.addComponent(new SphereColliderComponent(10));

    }
}