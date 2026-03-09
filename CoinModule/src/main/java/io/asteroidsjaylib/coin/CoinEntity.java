package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.coincommon.CoinSPI;
import io.asteroidsjaylib.coincommon.CoinTag;
import io.asteroidsjaylib.collisioncommon.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.physicscommon.PositionComponent;
import io.asteroidsjaylib.physicscommon.VelocityComponent;
import io.asteroidsjaylib.rendercommon.RenderTag;
import io.asteroidsjaylib.rendercommon.ShapeComponent;
import io.asteroidsjaylib.rendercommon.TextComponent;
import io.asteroidsjaylib.rendercommon.shapes.Ellipse;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class CoinEntity extends BaseEntity implements CoinSPI {

    public CoinEntity(){}

    public CoinEntity(Vector startPosition, Vector startVelocity, int value) {
        this.addComponent(new PositionComponent(startPosition));
        this.addComponent(new VelocityComponent(startVelocity));
        this.addComponent(new CoinTag(value));

        RenderTag renderTag = new RenderTag(10);
        ShapeComponent shapeComponent = new ShapeComponent(new Ellipse(16, 16, YELLOW));
        renderTag.addRenderComponent(shapeComponent, 0);

        String text = value+"";
        TextComponent textComponent = new TextComponent(text, 12, BLACK);
        textComponent.xoffset = -(double) MeasureText(text, 12) / 2;
        textComponent.yoffset = -12.0 / 2;
        renderTag.addRenderComponent(textComponent, 1);

        this.addComponent(renderTag);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 8;
        this.addComponent(circleColliderComponent);

    }

    @Override
    public BaseEntity createCoin(Vector startPosition, Vector startVelocity, int value) {
        return new CoinEntity(startPosition, startVelocity, value);
    }
}