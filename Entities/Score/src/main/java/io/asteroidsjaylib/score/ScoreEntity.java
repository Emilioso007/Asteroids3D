package io.asteroidsjaylib.score;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics2d.PositionComponent;
import io.asteroidsjaylib.common.score.ScoreTag;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.render.TextComponent;

import static com.raylib.Colors.WHITE;

public class ScoreEntity extends BaseEntity {

    public ScoreEntity(){

        this.addComponent(new ScoreTag());

        RenderTag renderTag = new RenderTag(100, true);

        TextComponent textComponent = new TextComponent("Score: ", 24, WHITE);
        renderTag.addRenderComponent(textComponent, 0);
        this.addComponent(renderTag);

        this.addComponent(new PositionComponent(new Vector2D(10, 10)));

    }

}
