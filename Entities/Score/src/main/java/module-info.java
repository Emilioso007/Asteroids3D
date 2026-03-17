import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.score.ScoreProvider;
import io.asteroidsjaylib.score.ScoreSystem;

module Score {
    requires Common;
    requires CommonRender;
    requires jaylib;
    requires CommonScore;
    requires CommonPhysics;

    provides EntitySpi with ScoreProvider;
    provides BaseSystem with ScoreSystem;
}