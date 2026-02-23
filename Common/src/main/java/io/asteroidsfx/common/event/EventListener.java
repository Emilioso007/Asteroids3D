package io.asteroidsfx.common.event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
