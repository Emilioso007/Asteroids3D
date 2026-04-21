module CommonLifetime {
    requires Common;
    requires spring.beans;
    requires spring.context;
    exports io.asteroidsjaylib.common.lifetime;

    opens io.asteroidsjaylib.common.lifetime to spring.core, spring.beans, spring.context;
}