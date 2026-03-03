package io.asteroidsfx.common.event;

import io.asteroidsfx.common.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventBus {

    private final Map<Class<? extends BaseEvent>, List<EventListener>> listeners = new HashMap<>();

    public <T extends BaseEvent> void subscribe(Class<T> eventType, EventListener<T> listener){

        listeners.putIfAbsent(eventType, new ArrayList<>());

        listeners.get(eventType).add(listener);

    }

    public void publish(World world, BaseEvent event){
        Class<? extends BaseEvent> eventType = event.getClass();

        List<EventListener> registeredListeners = listeners.get(eventType);

        if (registeredListeners != null){

            for (EventListener listener : registeredListeners){

                listener.onEvent(world, event);

            }

        }

    }

    public void clear() {
        listeners.clear();
    }
}
