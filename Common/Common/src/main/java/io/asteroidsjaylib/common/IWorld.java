package io.asteroidsjaylib.common;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.IEventBus;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public interface IWorld {
    IEventBus getEventBus();

    void tick(float deltaTime);

    void addEntity(BaseEntity entity);
    void removeEntity(BaseEntity entity);
    void addSystem(BaseSystem system);
    void removeSystem(BaseSystem system);

    <T extends BaseComponent> boolean hasEntitiesWith(Class<T> requiredComponent);

    List<BaseEntity> getEntitiesWith(Class<? extends BaseComponent>... requiredComponents);

    void queueAddEntity(BaseEntity entityToSpawn);

    int getWidth();

    int getHeight();

    float getDeltaTime();

    void setWidth(int width);

    void setHeight(int height);

    void clearEntities();

    void clearSystems();

    Raylib.Camera3D getCamera();

    void setCameraLocation(Vector3D cameraLocation);

    void shakeCamera(Vector3D shakeVector);

    int getScreenWidth();

    void setScreenWidth(int screenWidth);

    int getScreenHeight();

    void setScreenHeight(int screenHeight);

    Vector3D getCameraShake();

}
