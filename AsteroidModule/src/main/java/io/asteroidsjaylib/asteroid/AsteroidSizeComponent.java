package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class AsteroidSizeComponent extends BaseComponent {
    public static final int LARGE = 2;
    public static final int MEDIUM = 1;
    public static final int SMALL = 0;
    public int size = LARGE;
}
