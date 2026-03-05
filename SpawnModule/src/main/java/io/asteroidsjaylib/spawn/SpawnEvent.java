package io.asteroidsjaylib.spawn;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.BaseEvent;

public class SpawnEvent extends BaseEvent {
    public BaseEntity entityToSpawn;
}
