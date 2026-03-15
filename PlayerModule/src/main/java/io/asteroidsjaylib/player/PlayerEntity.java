package io.asteroidsjaylib.player;

import io.asteroidsjaylib.collisioncommon.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.outofboundscommon.BoundsAction;
import io.asteroidsjaylib.outofboundscommon.OutOfBoundsComponent;
import io.asteroidsjaylib.physicscommon.*;
import io.asteroidsjaylib.playercommon.PlayerTag;
import io.asteroidsjaylib.rendercommon.ImageComponent;
import io.asteroidsjaylib.rendercommon.RenderAlign;
import io.asteroidsjaylib.rendercommon.RenderTag;

public class PlayerEntity extends BaseEntity {

    public PlayerEntity(Vector startPosition){

        this.addComponent(new PlayerTag());

        PositionComponent positionComponent = new PositionComponent(startPosition);
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
        dragComponent.drag = 0.25F;
        this.addComponent(dragComponent);

        RenderTag renderTag = new RenderTag(50);
        ImageComponent imageComponent = new ImageComponent("spaceship.png", 76, 76);
        imageComponent.horizontalAlign = RenderAlign.CENTER;
        imageComponent.verticalAlign = RenderAlign.CENTER;
        renderTag.addRenderComponent(imageComponent, 0);
        this.addComponent(renderTag);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 38;

        this.addComponent(circleColliderComponent);

    }

}