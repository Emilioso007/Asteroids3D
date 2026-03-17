package io.asteroidsjaylib.common.event;

import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.event.input.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.KeyReleasedEvent;

import java.util.*;

import static com.raylib.Raylib.*;

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

    private static final int[] MONITORED_KEYS = {
            KEY_W, KEY_A, KEY_S, KEY_D,
            KEY_UP, KEY_LEFT, KEY_DOWN, KEY_RIGHT,
            KEY_SPACE, KEY_R
    };

    public void updateInputBus(World world) {
        for (int key : MONITORED_KEYS) {
            if (IsKeyPressed(key)) {
                publish(world, new KeyPressedEvent(key));
            }

            if (IsKeyReleased(key)) {
                publish(world, new KeyReleasedEvent(key));
            }
        }
    }

    public void clear() {
        listeners.clear();
    }
}
