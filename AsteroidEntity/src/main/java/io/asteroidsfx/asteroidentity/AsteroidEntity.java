package io.asteroidsfx.asteroidentity;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.circlecollidercomponent.CircleColliderComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.Polygon;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import io.asteroidsfx.rotationcomponent.RotationComponent;
import io.asteroidsfx.velocitycomponent.VelocityComponent;
import io.asteroidsfx.wraparoundsystem.WrapComponent;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Random;

public class AsteroidEntity extends Entity{

    public AsteroidEntity(int maxX, int maxY) { this(0, maxX, 0, maxY); }
    public AsteroidEntity(int minX, int maxX, int minY, int maxY){
        Random random = new Random();

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.x = random.nextInt(minX, maxX);
        positionComponent.y = random.nextInt(minY, maxY);

        this.components.add(positionComponent);

        VelocityComponent velocityComponent = VelocityComponent.fromAngleWithSpeed(random.nextDouble(Math.PI*2), random.nextDouble(50, 250));

        this.components.add(velocityComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;

        this.components.add(angleComponent);

        RotationComponent rotationComponent = new RotationComponent();
        rotationComponent.dAngle = Math.toRadians(random.nextInt(45, 135));

        this.components.add(rotationComponent);

        RenderComponent renderComponent = new RenderComponent();

        int points = random.nextInt(4, 13);
        double[] xs = new double[points];
        double[] ys = new double[points];

        double angleBetween = Math.toRadians(360f/points);

        for(int i = 0; i < points; i++){
            xs[i] = Math.cos(i*angleBetween)*random.nextInt(25, 75);
            ys[i] = Math.sin(i*angleBetween)*random.nextInt(25, 75);
        }

        renderComponent.polygon = new Polygon(xs, ys, Color.DARKGRAY, Color.GRAY, 2);

        this.components.add(renderComponent);

        WrapComponent wrapComponent = new WrapComponent();
        int buffer = 50; // a buffer to counteract the rotation
        wrapComponent.rightExtent = (int) Arrays.stream(renderComponent.polygon.x).max().orElse(0) + buffer;
        wrapComponent.leftExtent = (int) Arrays.stream(renderComponent.polygon.x).min().orElse(0) - buffer;
        wrapComponent.bottomExtent = (int) Arrays.stream(renderComponent.polygon.y).max().orElse(0) + buffer;
        wrapComponent.topExtent = (int) Arrays.stream(renderComponent.polygon.y).min().orElse(0) - buffer;
        wrapComponent.wrapOutside = true;

        this.components.add(wrapComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 50;

        this.components.add(circleColliderComponent);

    }

}