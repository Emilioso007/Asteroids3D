package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.lifetime.Lifetime;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.render.LightManager;
import io.asteroidsjaylib.common.util.ITimeProvider;
import io.asteroidsjaylib.common.util.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BulletGlowSystem extends IteratingSystem {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ITimeProvider timeProvider;

    @Override
    public void update(IWorld world, BaseEntity entity, float deltaTime) {
        Position position = entity.get(Position.class);
        Lifetime lifetime = entity.get(Lifetime.class);
        assert position != null;
        assert lifetime != null;

        Vector3D pos = position.vector();
        float startTime = lifetime.start();
        float lifeTime = lifetime.duration();

        float decay = map(timeProvider.getTime() - startTime, 0, lifeTime, 1, 0);

        float red = 1.0f * decay * 10.0f;
        float green = 0.5f * decay * 1.0f;
        float blue = 0.0f;

        LightManager.addLightSphere(pos.x, pos.y, pos.z, 100f, red, green, blue);
    }

    @Override
    public void start(IWorld world) {
        this.priority(99);
    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(BulletTag.class, Position.class, Lifetime.class);
    }

    private float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
