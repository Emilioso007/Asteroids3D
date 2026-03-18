
module Core {
    uses io.asteroidsjaylib.common.ecs.BaseSystem;
    uses io.asteroidsjaylib.common.ecs.EntitySpi;
    uses io.asteroidsjaylib.common.button.ButtonSPI;
    uses io.asteroidsjaylib.common.ecs.IGameStateProvider;
    requires Common;
    requires jaylib;
    requires CommonButton;
    requires CommonScore;
}