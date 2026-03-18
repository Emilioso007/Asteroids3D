import io.asteroidsjaylib.common.ecs.IGameStateProvider;
import io.asteroidsjaylib.state.playing.Playing;

module Playing {
    uses io.asteroidsjaylib.common.ecs.EntitySpi;
    uses io.asteroidsjaylib.common.ecs.BaseSystem;
    requires Common;
    requires jaylib;

    provides IGameStateProvider with Playing;
}