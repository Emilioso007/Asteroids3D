package io.asteroidsjaylib.spawn;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.spawn.SpawnEvent;
import org.springframework.context.event.EventListener;

public class SpawnSystem extends ResponseSystem {

    private IWorld world;

    @Override
    public void start(IWorld world){
        this.world = world;
    }

    @EventListener
    private void handleSpawnEvent(SpawnEvent event) {
        System.out.println(event.entityToSpawn.getClass().getName());
        this.world.queueAddEntity(event.entityToSpawn);
    }

}
