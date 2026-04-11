package io.asteroidsjaylib.common.physics2d;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class DragComponent extends BaseComponent {
    /// The percentage of velocity that remains after 1 second (0.0 = instant stop, 1.0 = no friction).
    public float drag;
}