package io.asteroidsfx.enemy;

import io.asteroidsfx.bullet.BulletEntity;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.IntervalIteratingSystem;
import io.asteroidsfx.physics.component.PositionComponent;
import io.asteroidsfx.player.PlayerTag;
import io.asteroidsfx.spawn.SpawnEvent;

import java.util.List;

public class EnemySystem extends IntervalIteratingSystem {

    @Override
    public void start(World world) {
        this.interval = 2.0;
    }

    @Override
    public void updateInterval(BaseEntity enemy, double deltaTime) {
        if(!World.getInstance().hasEntitiesWith(PlayerTag.class)) return;

        BaseEntity player = World.getInstance().getEntitiesWith(PlayerTag.class).getFirst();

        PositionComponent enemyPosition = enemy.getComponent(PositionComponent.class);
        PositionComponent playerPosition = player.getComponent(PositionComponent.class);

        if(Vector.dist(enemyPosition.pos, playerPosition.pos) > 300) return;

        Vector bulletStart = enemyPosition.pos.copy();
        Vector bulletVelocity = playerPosition.pos.copy().sub(enemyPosition.pos).setMag(400);

        BulletEntity bullet = new BulletEntity(enemy, bulletStart, bulletVelocity);
        SpawnEvent event = new SpawnEvent();
        event.entityToSpawn = bullet;
        World.getInstance().getEventBus().publish(event);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(EnemyTag.class);
    }

}
