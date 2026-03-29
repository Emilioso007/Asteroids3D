package io.asteroidsjaylib.score;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.render.TextComponent;
import io.asteroidsjaylib.common.score.IncrementScoreEvent;
import io.asteroidsjaylib.common.score.ScoreTag;

import java.util.List;

public class ScoreSystem extends IteratingSystem {
    @Override
    public void start(IWorld world) {
        world.getEventBus().subscribe(IncrementScoreEvent.class, this::handleIncrementScore);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(ScoreTag.class);
    }

    private void handleIncrementScore(IWorld world, IncrementScoreEvent incrementScoreEvent) {

        BaseEntity scoreEntity = world.getEntitiesWith(ScoreTag.class).getFirst();

        scoreEntity.getComponent(ScoreTag.class).orElseThrow().score += incrementScoreEvent.amount;

    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {

        if(!entity.hasComponents(RenderTag.class)) return;

        RenderTag renderTag = entity.getComponent(RenderTag.class).orElseThrow();

        if(!renderTag.hasRenderComponent(TextComponent.class)) return;

        ScoreTag scoreTag = entity.getComponent(ScoreTag.class).orElseThrow();

        TextComponent textComponent = renderTag.getRenderComponent(TextComponent.class);
        textComponent.text = "Score: " + scoreTag.score;

    }
}
