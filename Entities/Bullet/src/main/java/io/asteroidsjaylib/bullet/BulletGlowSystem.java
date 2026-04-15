package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.lifetime.LifetimeComponent;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.render.LightManager;
import io.asteroidsjaylib.common.util.Vector3D;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class BulletGlowSystem extends IteratingSystem {
    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        Vector3D pos = entity.getComponent(PositionComponent.class).pos;
        Instant startTime = entity.getComponent(LifetimeComponent.class).startTime;
        Duration lifeTime = entity.getComponent(LifetimeComponent.class).lifetime;

        float decay = map(Instant.now().toEpochMilli() - startTime.toEpochMilli(), 0, lifeTime.toMillis(), 1, 0);

        float red = 1.0f * decay * 10.0f;
        float green = 0.5f * decay * 1.0f;
        float blue = 0.0f;

        LightManager.addLightSphere(pos.x, pos.y, pos.z, 100f, red, green, blue);
    }

    @Override
    public void start(IWorld world) {
        this.setPriority(99);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(BulletTag.class, PositionComponent.class, LifetimeComponent.class);
    }

    private float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
