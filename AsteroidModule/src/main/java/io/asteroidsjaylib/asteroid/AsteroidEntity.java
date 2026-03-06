package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.outofbounds.BoundsAction;
import io.asteroidsjaylib.outofbounds.OutOfBoundsComponent;
import io.asteroidsjaylib.physics.component.AngleComponent;
import io.asteroidsjaylib.physics.component.PositionComponent;
import io.asteroidsjaylib.physics.component.RotationComponent;
import io.asteroidsjaylib.physics.component.VelocityComponent;
import io.asteroidsjaylib.render.component.ShapeComponent;
import io.asteroidsjaylib.render.shapes.BaseShape;
import io.asteroidsjaylib.render.shapes.Polygon;

import static com.raylib.Colors.*;

import java.util.Random;

public class AsteroidEntity extends BaseEntity {

    public AsteroidEntity(Vector startPosition, int size){

        this.addComponent(new AsteroidTag());

        AsteroidSizeComponent asteroidSizeComponent = new AsteroidSizeComponent();
        asteroidSizeComponent.size = size;
        this.addComponent(asteroidSizeComponent);

        Random random = new Random();

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = startPosition;
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = Vector.fromAngle(random.nextDouble(Math.PI*2)).setMag(random.nextDouble(50, 250));
        this.addComponent(velocityComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;
        this.addComponent(angleComponent);

        RotationComponent rotationComponent = new RotationComponent();
        rotationComponent.dAngle = Math.toRadians(random.nextInt(45, 135));
        this.addComponent(rotationComponent);

        int points = random.nextInt(4, 13);
        double[] xs = new double[points];
        double[] ys = new double[points];

        double angleBetween = Math.toRadians(360f/points);

        int min = 15 + size * 10;
        int max = 35 + size * 20;

        for(int i = 0; i < points; i++){
            xs[i] = Math.cos(i*angleBetween)*random.nextInt(min, max);
            ys[i] = Math.sin(i*angleBetween)*random.nextInt(min, max);
        }

        BaseShape shape = new Polygon(xs, ys, DARKGRAY, GRAY, 2);
        ShapeComponent shapeComponent = new ShapeComponent(shape);
        this.addComponent(shapeComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        //outOfBoundsComponent.rightExtent = 100;
        //outOfBoundsComponent.leftExtent = -100;
        //outOfBoundsComponent.bottomExtent = 100;
        //outOfBoundsComponent.topExtent = -100;
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = (35 + size * 20)/2d;
        this.addComponent(circleColliderComponent);

    }

}