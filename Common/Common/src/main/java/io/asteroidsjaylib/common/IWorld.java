package io.asteroidsjaylib.common;

import com.raylib.Camera3D;
import io.asteroidsjaylib.common.ecs.*;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public interface IWorld {
    void tick(float deltaTime);
    void addEntity(BaseEntity entity);
    void removeEntity(BaseEntity entity);
    void addSystem(BaseSystem system);
    void removeSystem(BaseSystem system);
    <T extends BaseComponent> boolean hasEntitiesWith(Class<T> requiredComponent);
    List<BaseEntity> getEntitiesWith(Class<? extends BaseComponent> componentType);
    List<BaseEntity> getEntitiesWith(Class<? extends BaseComponent>... requiredComponents);
    float getDeltaTime();
    void clearEntities();
    void clearSystems();
    Camera3D getCamera();
    void setCameraLocation(Vector3D cameraLocation);
    int getScreenWidth();
    void setScreenWidth(int screenWidth);
    int getScreenHeight();
    void setScreenHeight(int screenHeight);
    float getWorldSize();
}
