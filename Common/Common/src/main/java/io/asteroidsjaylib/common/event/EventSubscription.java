package io.asteroidsjaylib.common.event;

public record EventSubscription<T extends BaseEvent>(Class<T> eventType, EventListener<T> listener) {
}
