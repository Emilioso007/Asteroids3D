package io.asteroidsjaylib.common;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.IEventBus;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.List;
import java.util.Set;

public interface IWorld {
    IEventBus getEventBus();

    void tick(float deltaTime);

    boolean addEntity(BaseEntity entity);

    boolean addSystem(BaseSystem system);

    <T extends BaseComponent> boolean hasEntitiesWith(Class<T> requiredComponent);

    List<BaseEntity> getEntitiesWith(Class<? extends BaseComponent>... requiredComponents);

    void queueAddEntity(BaseEntity entityToSpawn);

    int getWidth();

    int getHeight();

    List<BaseEntity> getEntities();

    Set<BaseSystem> getSystems();

    double getDeltaTime();

    void setWidth(int width);

    void setHeight(int height);

    void clearEntities();

    void clearSystems();


    Raylib.Camera2D getCamera();

    void setCameraLocation(Vector2D cameraLocation);

    void shakeCamera(Vector2D shakeVector);

    int getScreenWidth();

    void setScreenWidth(int screenWidth);

    int getScreenHeight();

    void setScreenHeight(int screenHeight);

    Vector2D getCameraShake();
}
