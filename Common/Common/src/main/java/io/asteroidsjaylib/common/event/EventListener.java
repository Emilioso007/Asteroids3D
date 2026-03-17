package io.asteroidsjaylib.common.event;

import io.asteroidsjaylib.common.IWorld;

public interface EventListener<T extends BaseEvent> {
    void onEvent(IWorld world, T event);
}
