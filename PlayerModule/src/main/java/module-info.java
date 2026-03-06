import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.player.PlayerCollisionResponseSystem;
import io.asteroidsjaylib.player.PlayerEntityProvider;
import io.asteroidsjaylib.player.PlayerMovementSystem;
import io.asteroidsjaylib.player.PlayerShootingSystem;

module Player {
    exports io.asteroidsjaylib.player;
    requires Common;
    requires OutOfBounds;
    requires Collision;
    requires Bullet;
    requires Spawn;
    requires Physics;
    requires Ownership;
    requires jaylib;
    requires Render;

    provides EntitySpi with PlayerEntityProvider;
    provides BaseSystem with PlayerShootingSystem, PlayerMovementSystem, PlayerCollisionResponseSystem;
}