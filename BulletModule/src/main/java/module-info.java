import io.asteroidsjaylib.bullet.BulletCollisionResponseSystem;
import io.asteroidsjaylib.common.ecs.BaseSystem;

module Bullet {
    requires Common;
    requires RenderComponent;
    requires OutOfBounds;
    requires Collision;
    requires Physics;
    requires Ownership;
    requires Lifetime;

    requires jaylib;

    exports io.asteroidsjaylib.bullet;

    provides BaseSystem with BulletCollisionResponseSystem;
}