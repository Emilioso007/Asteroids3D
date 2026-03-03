package io.asteroidsfx.enemy;

import io.asteroidsfx.collision.CircleColliderComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.shapes.Ellipse;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.common.World;
import io.asteroidsfx.outofbounds.BoundsAction;
import io.asteroidsfx.outofbounds.OutOfBoundsComponent;
import io.asteroidsfx.physics.component.AngleComponent;
import io.asteroidsfx.physics.component.PositionComponent;
import io.asteroidsfx.physics.component.VelocityComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.paint.Color;

public class EnemyEntity extends BaseEntity {

    public EnemyEntity(World world){

        this.addComponent(new EnemyTag());

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = new Vector(world.getWidth() * 0.25f, world.getHeight() * 0.5f);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = new Vector(50, 0);
        this.addComponent(velocityComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;
        this.addComponent(angleComponent);

        RenderComponent renderComponent = new RenderComponent();
        renderComponent.shape = new Ellipse(60, 40, Color.DARKRED, Color.RED, 4);
        this.addComponent(renderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 25;
        this.addComponent(circleColliderComponent);
    }

}
