
module Core {
    uses io.asteroidsjaylib.common.ecs.BaseSystem;
    uses io.asteroidsjaylib.common.ecs.EntitySpi;

    requires Common;
    requires io.github.electronstudio.jaylib.ffm;
    requires CommonPlayer;
    requires CommonPhysics3D;
    requires CommonEnemy;

    requires spring.context;
    requires spring.beans;

    opens io.asteroidsjaylib to spring.core, spring.beans, spring.context;
}