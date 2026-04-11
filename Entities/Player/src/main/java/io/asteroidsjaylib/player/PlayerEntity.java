package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.*;
import io.asteroidsjaylib.common.render.AnimationImageComponent;
import io.asteroidsjaylib.common.sound.SoundComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.RenderAlign;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.outofbounds.BoundsAction;
import io.asteroidsjaylib.common.outofbounds.OutOfBoundsComponent;
import io.asteroidsjaylib.common.util.Vector3D;

public class PlayerEntity extends BaseEntity {

    public PlayerEntity(Vector3D startPosition){

        this.addComponent(new PlayerTag());

        PositionComponent positionComponent = new PositionComponent(startPosition);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        this.addComponent(velocityComponent);

        AccelerationComponent accelerationComponent = new AccelerationComponent();
        this.addComponent(accelerationComponent);

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