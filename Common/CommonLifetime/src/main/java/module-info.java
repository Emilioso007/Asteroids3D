module CommonLifetime {
    requires Common;
    exports io.asteroidsjaylib.common.lifetime;

    opens io.asteroidsjaylib.common.lifetime to spring.core, spring.beans, spring.context;
}