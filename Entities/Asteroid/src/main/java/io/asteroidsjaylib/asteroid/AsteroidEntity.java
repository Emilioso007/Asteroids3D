package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidType;
import io.asteroidsjaylib.common.asteroid.AsteroidTypeComponent;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.lifetime.LifetimeComponent;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.physics3d.VelocityComponent;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.shapes3d.Model3D;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

import java.time.Duration;
import java.util.Random;

public class AsteroidEntity extends BaseEntity {

    public AsteroidEntity(Vector3D startPosition, Vector3D startVelocity, AsteroidType type){

        this.addComponent(new AsteroidTag());

        this.addComponent(new AsteroidTypeComponent(type));

        Random random = new Random();

        this.addComponent(new PositionComponent(startPosition));

        this.addComponent(new VelocityComponent(startVelocity));

        float x = random.nextFloat(-1, 1);
        float y = random.nextFloat(-1, 1);
        float z = random.nextFloat(-1, 1);
        float w = 1;

        this.addComponent(new RotationComponent(new Quaternion(x, y, z, w)));

        Render3DComponent render3DComponent = new Render3DComponent();


        String path = switch (type){
            case Full -> "/LegoAsteroid.glb";
            case Top -> "/LegoAsteroidTop.glb";
            case Bottom -> "/LegoAsteroidBottom.glb";
        };

        Model3D asteroid = new Model3D(path, 1, 90, -90, 0);
        asteroid.applyShader(ShaderManager.getShader("solid"));
        render3DComponent.addShape(asteroid);
        this.addComponent(render3DComponent);

        if (type == AsteroidType.Full){
            this.addComponent(new SphereColliderComponent(40));
        } else {
            this.addComponent(new LifetimeComponent(Duration.ofSeconds(5)));
        }

    }

}