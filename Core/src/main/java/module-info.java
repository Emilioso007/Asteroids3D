
module Core {
    uses io.asteroidsjaylib.common.ecs.BaseSystem;
    uses io.asteroidsjaylib.common.ecs.EntitySpi;
    uses io.asteroidsjaylib.common.event.EventSubscriberSPI;

    requires Common;
    requires jaylib;
    requires CommonPlayer;
    requires CommonPhysics3D;
    requires CommonEnemy;

    requires spring.context;
    requires spring.beans;
    requires spring.core;

    opens io.asteroidsjaylib to spring.core, spring.beans, spring.context;
}