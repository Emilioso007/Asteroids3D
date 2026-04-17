package io.asteroidsjaylib.spawn;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventSubscriberSPI;
import io.asteroidsjaylib.common.event.EventSubscription;
import io.asteroidsjaylib.common.spawn.SpawnEvent;

import java.util.List;

public class SpawnSystem implements EventSubscriberSPI {

    @Override
    public List<EventSubscription<? extends BaseEvent>> getEventSubscriptions() {
        return List.of(new EventSubscription<>(SpawnEvent.class, this::handleSpawnEvent));
    }

    private void handleSpawnEvent(IWorld world, SpawnEvent event) {
        System.out.println(event.entityToSpawn.getClass().getName());
        world.queueAddEntity(event.entityToSpawn);
    }

}
