package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics.*;
import io.asteroidsjaylib.common.render.AnimationImageComponent;
import io.asteroidsjaylib.common.sound.SoundComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.RenderAlign;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.outofbounds.BoundsAction;
import io.asteroidsjaylib.common.outofbounds.OutOfBoundsComponent;

public class PlayerEntity extends BaseEntity {

    public PlayerEntity(Vector2D startPosition){

        this.addComponent(new PlayerTag());

        PositionComponent positionComponent = new PositionComponent(startPosition);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = new Vector2D();
        this.addComponent(velocityComponent);

        AccelerationComponent accelerationComponent = new AccelerationComponent();
        accelerationComponent.acc = new Vector2D();
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
        AnimationImageComponent animationImageComponent = new AnimationImageComponent("spaceship-animation.png", 75, 75, 4);
        animationImageComponent.horizontalAlign = RenderAlign.CENTER;
        animationImageComponent.verticalAlign = RenderAlign.CENTER;
        renderTag.addRenderComponent(animationImageComponent, 0);
        this.addComponent(renderTag);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 75/2f;

        this.addComponent(circleColliderComponent);

        SoundComponent soundComponent = new SoundComponent("rocket-sound.wav");
        this.addComponent(soundComponent);

    }

}