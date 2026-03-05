package io.asteroidsjaylib.outofbounds;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class OutOfBoundsComponent extends BaseComponent {
    public BoundsAction boundsAction;
    public double leftExtent;
    public double rightExtent;
    public double topExtent;
    public double bottomExtent;
}
