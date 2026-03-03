package io.asteroidsfx.asteroid;

import io.asteroidsfx.collision.CircleColliderComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.shapes.Polygon;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.outofbounds.BoundsAction;
import io.asteroidsfx.outofbounds.OutOfBoundsComponent;
import io.asteroidsfx.physics.component.AngleComponent;
import io.asteroidsfx.physics.component.PositionComponent;
import io.asteroidsfx.physics.component.RotationComponent;
import io.asteroidsfx.physics.component.VelocityComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.paint.Color;

import java.util.Random;

public class AsteroidEntity extends BaseEntity {

    public AsteroidEntity(Vector startPosition){

        this.addComponent(new AsteroidTag());
        this.addComponent(new AsteroidSizeComponent());

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

        RenderComponent renderComponent = new RenderComponent();

        int points = random.nextInt(4, 13);
        double[] xs = new double[points];
        double[] ys = new double[points];

        double angleBetween = Math.toRadians(360f/points);

        for(int i = 0; i < points; i++){
            xs[i] = Math.cos(i*angleBetween)*random.nextInt(25, 75);
            ys[i] = Math.sin(i*angleBetween)*random.nextInt(25, 75);
        }

        renderComponent.shape = new Polygon(xs, ys, Color.DARKGRAY, Color.GRAY, 2);
        this.addComponent(renderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        //outOfBoundsComponent.rightExtent = 100;
        //outOfBoundsComponent.leftExtent = -100;
        //outOfBoundsComponent.bottomExtent = 100;
        //outOfBoundsComponent.topExtent = -100;
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 50;
        this.addComponent(circleColliderComponent);

    }

}