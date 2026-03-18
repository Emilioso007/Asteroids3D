package io.asteroidsjaylib.common.event;

public class StateChangedEvent extends BaseEvent{
    public final String newState;

    public StateChangedEvent(String newState) {
        this.newState = newState;
    }
}
