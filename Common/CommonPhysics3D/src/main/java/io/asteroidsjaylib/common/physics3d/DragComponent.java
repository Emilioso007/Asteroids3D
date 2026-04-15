package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class DragComponent extends BaseComponent {
    /// The percentage of velocity that remains after 1 second (0.0 = instant stop, 1.0 = no friction).
    public float drag;

    public DragComponent(float drag) {
        this.drag = drag;
    }
}