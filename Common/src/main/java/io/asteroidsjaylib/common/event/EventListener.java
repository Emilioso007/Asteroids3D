package io.asteroidsjaylib.common.event;

import io.asteroidsjaylib.common.World;

public interface EventListener<T extends BaseEvent> {
    void onEvent(World world, T event);
}
