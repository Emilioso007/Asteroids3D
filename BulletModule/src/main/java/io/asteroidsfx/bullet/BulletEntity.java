package io.asteroidsfx.bullet;

import io.asteroidsfx.Ownership.OwnershipComponent;
import io.asteroidsfx.collision.CircleColliderComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.shapes.Ellipse;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.lifetime.LifetimeComponent;
import io.asteroidsfx.outofbounds.BoundsAction;
import io.asteroidsfx.outofbounds.OutOfBoundsComponent;
import io.asteroidsfx.physics.component.PositionComponent;
import io.asteroidsfx.physics.component.VelocityComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.paint.Color;

import java.time.Duration;

public class BulletEntity extends BaseEntity {

    public BulletEntity(BaseEntity owner, Vector startPosition, Vector velocity) {

        OwnershipComponent ownershipComponent = new OwnershipComponent();
        ownershipComponent.owner = owner;
        this.addComponent(ownershipComponent);

        this.addComponent(new BulletTag());

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = startPosition;
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = velocity;
        this.addComponent(velocityComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 2.5;
        this.addComponent(circleColliderComponent);

        RenderComponent renderComponent = new RenderComponent();
        renderComponent.shape = new Ellipse(5, 5, Color.LIGHTGRAY);
        this.addComponent(renderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        //outOfBoundsComponent.leftExtent = -2.5;
        //outOfBoundsComponent.rightExtent = 2.5;
        //outOfBoundsComponent.topExtent = -2.5;
        //outOfBoundsComponent.bottomExtent = 2.5;
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        LifetimeComponent lifetimeComponent = new LifetimeComponent(Duration.ofSeconds(2));
        this.addComponent(lifetimeComponent);

    }

}