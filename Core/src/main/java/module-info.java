
module Core {
    uses io.asteroidsjaylib.common.ecs.BaseSystem;
    uses io.asteroidsjaylib.common.ecs.EntitySpi;
    uses io.asteroidsjaylib.common.event.EventSubscriberSPI;
    requires Common;
    requires jaylib;
    requires CommonPlayer;
    requires CommonPhysics3D;
    requires CommonEnemy;
}