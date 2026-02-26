package io.asteroidsfx.TimerComponent;

import io.asteroidsfx.common.Component;

import java.time.Duration;
import java.time.Instant;

public class TimerComponent extends Component {
    public Instant instant = Instant.MIN;
    public Duration duration = Duration.ZERO;
}