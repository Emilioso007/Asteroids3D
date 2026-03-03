package io.asteroidsfx.spawn;

import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.ResponseSystem;

public class SpawnSystem extends ResponseSystem {

    @Override
    public void start(World world) {
        world.getEventBus().subscribe(SpawnEvent.class, this::handleSpawnEvent);
    }

    private void handleSpawnEvent(World world, SpawnEvent event) {
        world.queueAddEntity(event.entityToSpawn);
    }

}
