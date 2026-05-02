import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.systems.score.ScoreSystem;

module Score {
    requires Common;
    requires spring.context;
    requires CommonScore;
    requires spring.web;

    opens io.asteroidsjaylib.systems.score to spring.core;

    provides BaseSystem with ScoreSystem;
}