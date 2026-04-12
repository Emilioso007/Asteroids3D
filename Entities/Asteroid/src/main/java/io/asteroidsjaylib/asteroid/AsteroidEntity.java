package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidSize;
import io.asteroidsjaylib.common.asteroid.AsteroidSizeComponent;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.shapes3d.Model3D;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.Random;

public class AsteroidEntity extends BaseEntity {

    public AsteroidEntity(Vector3D startPosition, Vector3D startVelocity, AsteroidSize size){

        this.addComponent(new AsteroidTag());

        AsteroidSizeComponent asteroidSizeComponent = new AsteroidSizeComponent();
        asteroidSizeComponent.size = size;
        this.addComponent(asteroidSizeComponent);

        Random random = new Random();

        PositionComponent positionComponent = new PositionComponent(startPosition);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent(startVelocity);
        this.addComponent(velocityComponent);

        RotationComponent rotationComponent = new RotationComponent();
        this.addComponent(rotationComponent);

        int min = 15 + size.ordinal() * 10;
        int max = 35 + size.ordinal() * 20;

        Render3DComponent render3DComponent = new Render3DComponent();
        float radius = random.nextFloat(min, max);
        /*
        Sphere3D sphere3D = new Sphere3D(radius, DARKGRAY, GRAY);
        render3DComponent.shapes.add(sphere3D);
         */
        Model3D asteroid = new Model3D("/LegoAsteroid.obj", "/LegoAsteroid.mtl", 1, 0, 0, 0);
        render3DComponent.shapes.add(asteroid);
        this.addComponent(render3DComponent);

        SphereColliderComponent sphereColliderComponent = new SphereColliderComponent(radius);
        this.addComponent(sphereColliderComponent);

    }

}