package io.asteroidsjaylib.common.event;

import io.asteroidsjaylib.common.IWorld;

public interface IEventBus {
    <T extends BaseEvent> void subscribe(Class<T> eventType, EventListener<T> listener);

    void publish(IWorld world, BaseEvent event);

    void clear();

    void findSubscribers();
}
