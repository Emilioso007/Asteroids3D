package io.asteroidsjaylib.background;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.shapes3d.Cube3D;
import io.asteroidsjaylib.common.util.Vector3D;

import static com.raylib.Colors.*;

import java.util.Random;

public class BackgroundEntity extends BaseEntity {

    public BackgroundEntity(){

        Render3DComponent render3DComponent = new Render3DComponent();

        Random random = new Random();
        for (int i = 0; i < 1500; i++){
            Cube3D star = new Cube3D(2, 2, 2, WHITE, BLANK);
            star.offset = new Vector3D(random.nextFloat(-5000, 5000),random.nextFloat(-5000, 5000),random.nextFloat(-5000, 5000));
            render3DComponent.shapes.add(star);
        }

        this.addComponent(render3DComponent);

    }

}
