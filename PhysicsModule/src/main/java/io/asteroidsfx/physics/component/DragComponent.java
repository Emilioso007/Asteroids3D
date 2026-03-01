package io.asteroidsfx.physics.component;

import io.asteroidsfx.common.ecs.BaseComponent;

public class DragComponent extends BaseComponent {
    /// The percentage of velocity that remains after 1 second (0.0 = instant stop, 1.0 = no friction).
    public double drag;
}