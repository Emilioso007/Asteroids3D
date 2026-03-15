package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.coincommon.CoinTag;
import io.asteroidsjaylib.collisioncommon.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.outofboundscommon.BoundsAction;
import io.asteroidsjaylib.outofboundscommon.OutOfBoundsComponent;
import io.asteroidsjaylib.physicscommon.PositionComponent;
import io.asteroidsjaylib.physicscommon.VelocityComponent;
import io.asteroidsjaylib.rendercommon.RenderAlign;
import io.asteroidsjaylib.rendercommon.RenderTag;
import io.asteroidsjaylib.rendercommon.ShapeComponent;
import io.asteroidsjaylib.rendercommon.TextComponent;
import io.asteroidsjaylib.rendercommon.shapes.Ellipse;

import static com.raylib.Colors.*;

public class CoinEntity extends BaseEntity {

    public CoinEntity(Vector startPosition, Vector startVelocity, int value) {
        this.addComponent(new PositionComponent(startPosition));
        this.addComponent(new VelocityComponent(startVelocity));
        this.addComponent(new CoinTag(value));

        RenderTag renderTag = new RenderTag(10);
        ShapeComponent shapeComponent = new ShapeComponent(new Ellipse(16, 16, YELLOW));
        renderTag.addRenderComponent(shapeComponent, 0);

        String text = value+"";
        TextComponent textComponent = new TextComponent(text, 12, BLACK);
        textComponent.horizontalAlign = RenderAlign.CENTER;
        textComponent.verticalAlign = RenderAlign.CENTER;
        renderTag.addRenderComponent(textComponent, 1);

        this.addComponent(renderTag);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 8;
        this.addComponent(circleColliderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

    }
}