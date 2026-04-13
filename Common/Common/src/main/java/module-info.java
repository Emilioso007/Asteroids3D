module Common {
    requires jaylib;
    requires jdk.jdi;
    exports io.asteroidsjaylib.common;
    exports io.asteroidsjaylib.common.ecs;
    exports io.asteroidsjaylib.common.event;
    exports io.asteroidsjaylib.common.util;
    exports io.asteroidsjaylib.common.event.input.key;
    exports io.asteroidsjaylib.common.event.input.mouse;
}