package io.asteroidsjaylib.common.event;

import java.util.List;

public interface EventSubscriberSPI {
    List<EventSubscription<? extends BaseEvent>> getEventSubscriptions();
}
