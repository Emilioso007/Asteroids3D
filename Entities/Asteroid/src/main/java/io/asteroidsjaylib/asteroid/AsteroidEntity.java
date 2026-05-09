package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidPart;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.collision.SphereCollider;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.lifetime.Lifetime;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Rotation;
import io.asteroidsjaylib.common.physics3d.Velocity;
import io.asteroidsjaylib.common.render.Render3D;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.Model3D;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

public class AsteroidEntity extends BaseEntity {

    public AsteroidEntity(Vector3D startPosition, Vector3D startVelocity, Quaternion rotation, AsteroidPart.Type type, float startTime){

        this.add(new AsteroidTag());

        this.add(new AsteroidPart(type));

        this.add(new Position().vector(startPosition));

        this.add(new Velocity().vector(startVelocity));

        this.add(new Rotation().quaternion(rotation));

        Render3D render3D = new Render3D();


        String path = switch (type){
            case Full -> "/LegoAsteroid.glb";
            case Top -> "/LegoAsteroidTop.glb";
            case Bottom -> "/LegoAsteroidBottom.glb";
        };

        Model3D asteroid = new Model3D(path, 1, 90, -90, 0);
        asteroid.applyShader(ShaderManager.getShader("solid"));
        render3D.addShape(asteroid);
        this.add(render3D);

        if (type == AsteroidPart.Type.Full){
            this.add(new SphereCollider().radius(40));
        } else {
            this.add(new Lifetime(startTime, 5));
        }

    }

}