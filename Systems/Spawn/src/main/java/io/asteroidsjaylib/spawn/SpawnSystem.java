package io.asteroidsjaylib.spawn;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.spawn.SpawnEvent;

public class SpawnSystem extends ResponseSystem {

    @Override
    public void start(IWorld world) {
        world.getEventBus().subscribe(SpawnEvent.class, this::handleSpawnEvent);
    }

    private void handleSpawnEvent(IWorld world, SpawnEvent event) {
        System.out.println(event.entityToSpawn.getClass().getName());
        world.queueAddEntity(event.entityToSpawn);
    }

}
