package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.event.BaseEvent;
import io.asteroidsjaylib.common.event.EventListener;
import io.asteroidsjaylib.common.event.IEventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Make a interface that responsesystems implement, and use servicelodaer to find listeners, IoC ftw!
public final class EventBus implements IEventBus {

    private final Map<Class<? extends BaseEvent>, List<EventListener>> listeners = new HashMap<>();

    @Override
    public <T extends BaseEvent> void subscribe(Class<T> eventType, EventListener<T> listener){

        listeners.putIfAbsent(eventType, new ArrayList<>());

        listeners.get(eventType).add(listener);

    }

    @Override
    public void publish(IWorld world, BaseEvent event){
        Class<? extends BaseEvent> eventType = event.getClass();

        List<EventListener> registeredListeners = listeners.get(eventType);

        if (registeredListeners != null){

            for (EventListener listener : registeredListeners){

                listener.onEvent(world, event);

            }

        }

    }

    @Override
    public void clear() {
        listeners.clear();
    }
}
