package io.asteroidsfx.spawn;

import io.asteroidsfx.TimerComponent.TimerComponent;
import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.event.EventBus;

import java.time.Instant;
import java.util.List;

public class SpawnSystem extends System {

    public SpawnSystem(EventBus eventBus) {
        eventBus.subscribe(SpawnEvent.class, this::handleSpawnEvent);
    }

    private void handleSpawnEvent(SpawnEvent event) {
        TimerComponent timerComponent = event.getComponent(TimerComponent.class);
        if (timerComponent != null) {
            if (timerComponent.instant.plus(timerComponent.duration).isBefore(Instant.now())) {
                timerComponent.instant = Instant.now();
                World.getInstance().queueAddEntity(event.entityToSpawn);
            }
        } else {
            World.getInstance().queueAddEntity(event.entityToSpawn);
        }
    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of();
    }

    @Override
    public void tick(float dt, List<Entity> entities) {

    }
}
