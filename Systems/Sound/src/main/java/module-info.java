import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.sound.SoundSystem;

module Sound {
    requires Common;
    requires CommonSound;
    requires jaylib;

    provides BaseSystem with SoundSystem;
}