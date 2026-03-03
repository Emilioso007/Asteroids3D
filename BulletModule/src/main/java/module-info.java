import io.asteroidsfx.bullet.BulletCollisionResponseSystem;
import io.asteroidsfx.common.ecs.BaseSystem;

module Bullet {
    requires Common;
    requires javafx.graphics;
    requires RenderComponent;
    requires OutOfBounds;
    requires Collision;
    requires Physics;
    requires Ownership;
    requires Lifetime;
    exports io.asteroidsfx.bullet;

    provides BaseSystem with BulletCollisionResponseSystem;
}