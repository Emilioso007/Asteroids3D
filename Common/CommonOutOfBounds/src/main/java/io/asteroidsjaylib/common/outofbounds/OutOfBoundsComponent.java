package io.asteroidsjaylib.common.outofbounds;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class OutOfBoundsComponent extends BaseComponent {
    public BoundsAction boundsAction;
    public float leftExtent;
    public float rightExtent;
    public float topExtent;
    public float bottomExtent;
}
