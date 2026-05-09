package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.collision.SphereCollider;
import io.asteroidsjaylib.common.lifetime.Lifetime;
import io.asteroidsjaylib.common.ownership.Ownership;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.Acceleration;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Rotation;
import io.asteroidsjaylib.common.physics3d.Velocity;
import io.asteroidsjaylib.common.render.Render3D;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.Model3D;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public class BulletEntity extends BaseEntity{

    public BulletEntity(BaseEntity owner, Vector3D startPosition, Vector3D velocity, Quaternion rotation, float startTime) {

        this.add(new Ownership(owner));

        this.add(new BulletTag());

        this.add(new Position().vector(startPosition));

        this.add(new Velocity().vector(velocity).terminal(2500));

        this.add(new Acceleration());

        this.add(new Rotation().quaternion(rotation));

        this.add(new Lifetime(startTime, 2));

        Render3D render3D = new Render3D();
        Model3D laser = new Model3D("/LegoBullet.glb", 1, 90, -90, 0);
        laser.applyShader(ShaderManager.getShader("solid"));
        render3D.addShape(laser);
        this.add(render3D);

        this.add(new SphereCollider().radius(10));

    }
}