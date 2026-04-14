package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.event.*;
import io.asteroidsjaylib.common.event.EventListener;

import java.util.*;

public final class EventBus implements IEventBus {

    private final Map<Class<? extends BaseEvent>, List<EventListener>> listeners = new HashMap<>();

    @Override
    public void findSubscribers() {
        ServiceLoader<EventSubscriberSPI> loader = ServiceLoader.load(EventSubscriberSPI.class);

        for (EventSubscriberSPI subscriber : loader){
            List<EventSubscription<? extends BaseEvent>> subscriptions = subscriber.getEventSubscriptions();

            if (subscriptions != null){
                for (EventSubscription<? extends BaseEvent> sub : subscriptions){
                    registerSubscription(sub);
                }
            }
        }
    }

    private <T extends BaseEvent> void registerSubscription(EventSubscription<T> sub) {
        subscribe(sub.eventType(), sub.listener());
    }

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
