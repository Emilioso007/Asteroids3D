package io.asteroidsfx.player;

import io.asteroidsfx.collision.CircleColliderComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.shapes.Polygon;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.outofbounds.BoundsAction;
import io.asteroidsfx.outofbounds.OutOfBoundsComponent;
import io.asteroidsfx.physics.component.*;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.paint.Color;

public class PlayerEntity extends BaseEntity {

    public PlayerEntity(Vector startPosition){

        this.addComponent(new PlayerTag());

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = startPosition;
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = new Vector();
        this.addComponent(velocityComponent);

        AccelerationComponent accelerationComponent = new AccelerationComponent();
        accelerationComponent.acc = new Vector();
        this.addComponent(accelerationComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;
        this.addComponent(angleComponent);

        RotationComponent rotationComponent = new RotationComponent();
        this.addComponent(rotationComponent);

        DragComponent dragComponent = new DragComponent();
        dragComponent.drag = 0.25;
        this.addComponent(dragComponent);


        RenderComponent renderComponent = new RenderComponent();

        double[] xs = new double[3];
        double[] ys = new double[3];

        for(int i = 0; i < 3; i++){
            xs[i] = Math.cos(Math.toRadians(i*360f/3))*40;
            ys[i] = Math.sin(Math.toRadians(i*360f/3))*40;
        }
        xs[0] = 60;

        renderComponent.shape = new Polygon(xs, ys, Color.BLUE, Color.CYAN, 4);

        this.addComponent(renderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        //outOfBoundsComponent.topExtent = -60;
        //outOfBoundsComponent.bottomExtent = 60;
        //outOfBoundsComponent.leftExtent = -60;
        //outOfBoundsComponent.rightExtent = 60;
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 50;

        this.addComponent(circleColliderComponent);

    }

}