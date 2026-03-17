package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.bullet.BulletSPI;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.IntervalIteratingSystem;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.spawn.SpawnEvent;

import java.util.List;
import java.util.ServiceLoader;

public class EnemySystem extends IntervalIteratingSystem {

    @Override
    public void start(World world) {
        this.setPriority(30);
        this.interval = 2.0;
    }

    @Override
    public void updateInterval(World world, BaseEntity enemy, double deltaTime) {
        if(!world.hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();

        PositionComponent enemyPosition = enemy.getComponent(PositionComponent.class).orElseThrow();
        PositionComponent playerPosition = player.getComponent(PositionComponent.class).orElseThrow();

        if(Vector2D.dist(enemyPosition.pos, playerPosition.pos) > 400) return;

        Vector2D bulletStart = enemyPosition.pos.copy();
        Vector2D bulletVelocity = playerPosition.pos.copy().sub(enemyPosition.pos).setMag(400);

        BulletSPI bulletSPI = ServiceLoader.load(BulletSPI.class).findFirst().orElseThrow();
        world.getEventBus().publish(world, new SpawnEvent(bulletSPI.CreateBullet(enemy, bulletStart, bulletVelocity)));

    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(EnemyTag.class);
    }

}
