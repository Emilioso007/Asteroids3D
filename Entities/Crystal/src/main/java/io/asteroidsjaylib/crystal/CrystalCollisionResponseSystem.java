package io.asteroidsjaylib.crystal;

import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.score.ScoreEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

public class CrystalCollisionResponseSystem extends ResponseSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @EventListener
    private void handleCollision(CollisionEvent collisionEvent) {
        if(!collisionEvent.hasEntityWith(CrystalTag.class)) return;

        BaseEntity crystal = collisionEvent.getEntityWith(CrystalTag.class);
        BaseEntity other = collisionEvent.getOther(crystal);

        if(!other.hasComponents(PlayerTag.class)) return;

        eventPublisher.publishEvent(new ScoreEvent(1));

        crystal.setToBeRemoved(true);
    }
}
