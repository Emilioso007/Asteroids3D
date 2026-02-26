package io.asteroidsfx.bulletentity;

import io.asteroidsfx.collision.CircleColliderComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.Polygon;
import io.asteroidsfx.common.Vector;
import io.asteroidsfx.outofbounds.BoundsAction;
import io.asteroidsfx.outofbounds.OutOfBoundsComponent;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import io.asteroidsfx.velocitycomponent.VelocityComponent;
import javafx.scene.paint.Color;

public class BulletEntity extends Entity {

    public BulletEntity(Vector startPosition, Vector velocity) {

        this.components.add(new BulletTag());

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = startPosition;
        this.components.add(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = velocity;
        this.components.add(velocityComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 2;
        this.components.add(circleColliderComponent);

        RenderComponent renderComponent = new RenderComponent();
        double[] xs = new double[6];
        double[] ys = new double[6];
        for(int i = 0; i < 6; i++){
            xs[i] = Math.cos(Math.toRadians(i*360f/6))*2;
            ys[i] = Math.sin(Math.toRadians(i*360f/6))*2;
        }
        renderComponent.polygon = new Polygon(xs, ys, Color.LIGHTGRAY, Color.TRANSPARENT, 0);
        this.components.add(renderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.leftExtent = 2;
        outOfBoundsComponent.rightExtent = 2;
        outOfBoundsComponent.topExtent = 2;
        outOfBoundsComponent.bottomExtent = 2;
        outOfBoundsComponent.boundsAction = BoundsAction.REMOVE;
        this.components.add(outOfBoundsComponent);

    }

}