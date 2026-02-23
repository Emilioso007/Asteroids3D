package io.asteroidsfx.bulletentity;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.circlecollidercomponent.CircleColliderComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.Polygon;
import io.asteroidsfx.linearvelocitycomponent.LinearVelocityComponent;
import io.asteroidsfx.outofboundscomponent.BoundsAction;
import io.asteroidsfx.outofboundscomponent.OutOfBoundsComponent;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.paint.Color;

public class BulletEntity extends Entity {

    public BulletEntity(double startX, double startY, double angle, double velocity){

        this.components.add(new BulletTag());

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.x = startX;
        positionComponent.y = startY;
        components.add(positionComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = angle;
        components.add(angleComponent);

        LinearVelocityComponent linearVelocityComponent = new LinearVelocityComponent();
        linearVelocityComponent.velocity = velocity;
        components.add(linearVelocityComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 2;
        components.add(circleColliderComponent);

        RenderComponent renderComponent = new RenderComponent();
        double[] xs = new double[6];
        double[] ys = new double[6];
        for(int i = 0; i < 6; i++){
            xs[i] = Math.cos(Math.toRadians(i*360f/6))*2;
            ys[i] = Math.sin(Math.toRadians(i*360f/6))*2;
        }
        renderComponent.polygon = new Polygon(xs, ys, Color.LIGHTGRAY, Color.TRANSPARENT, 0);
        components.add(renderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.leftExtent = 2;
        outOfBoundsComponent.rightExtent = 2;
        outOfBoundsComponent.topExtent = 2;
        outOfBoundsComponent.bottomExtent = 2;
        outOfBoundsComponent.boundsAction = BoundsAction.REMOVE;
        components.add(outOfBoundsComponent);

    }

}