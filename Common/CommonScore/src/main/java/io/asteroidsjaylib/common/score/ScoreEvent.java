package io.asteroidsjaylib.common.score;

import io.asteroidsjaylib.common.event.BaseEvent;

public class ScoreEvent extends BaseEvent {
    public int increment;

    public ScoreEvent(int increment) {
        this.increment = increment;
    }
}
