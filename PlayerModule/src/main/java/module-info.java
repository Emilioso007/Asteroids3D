import io.asteroidsfx.common.ecs.BaseSystem;
import io.asteroidsfx.common.ecs.EntitySpi;
import io.asteroidsfx.player.PlayerCollisionResponseSystem;
import io.asteroidsfx.player.PlayerEntityProvider;
import io.asteroidsfx.player.PlayerMovementSystem;
import io.asteroidsfx.player.PlayerShootingSystem;

module Player {
    exports io.asteroidsfx.player;
    requires Common;
    requires javafx.graphics;
    requires RenderComponent;
    requires OutOfBounds;
    requires Collision;
    requires Bullet;
    requires Spawn;
    requires Physics;
    requires Ownership;

    provides EntitySpi with PlayerEntityProvider;
    provides BaseSystem with PlayerShootingSystem, PlayerMovementSystem, PlayerCollisionResponseSystem;
}