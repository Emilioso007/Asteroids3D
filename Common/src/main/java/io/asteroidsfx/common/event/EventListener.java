package io.asteroidsfx.common.event;

import io.asteroidsfx.common.World;

public interface EventListener<T extends BaseEvent> {
    void onEvent(World world, T event);
}
