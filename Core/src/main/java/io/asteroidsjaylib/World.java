package io.asteroidsjaylib;

import com.raylib.Camera3D;
import com.raylib.Vector3;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.*;

import static com.raylib.Raylib.CameraProjection.CAMERA_PERSPECTIVE;

public final class World implements IWorld {

    private static final Vector3 RL_VEC_SCRATCHPAD = new Vector3();
    private int screenWidth;
    private int screenHeight;
    private final List<BaseEntity> entities;
    private final List<BaseEntity> entitiesToAdd;
    private final Set<BaseSystem> systems;
    private final Camera3D camera;
    private float deltaTime;
    private final Map<BaseSystem, List<BaseEntity>> systemEntityCache = new HashMap<>();
    private final float worldSize;

    public World(){
        this.camera = new Camera3D()
                .position(new Vector3D(0, 0, 2000).toVector3(RL_VEC_SCRATCHPAD))
                .target(new Vector3D(0, 0, 0).toVector3(RL_VEC_SCRATCHPAD))
                .up(new Vector3D(0, 0, 1).toVector3(RL_VEC_SCRATCHPAD))
                .fovy(45f)
                .projection(CAMERA_PERSPECTIVE);

        this.entities = new ArrayList<>();
        this.entitiesToAdd = new ArrayList<>();

        Comparator<BaseSystem> systemComparator =
                Comparator.comparing((BaseSystem s) -> s.priority())
                .thenComparing(system -> system.getClass().getName());

        this.systems = new TreeSet<>(systemComparator);

        this.worldSize = 10000;
    }

    @Override
    public void tick(float deltaTime){
        this.deltaTime = deltaTime;

        updateCacheIfNeeded();

        // Run all systems in priority order
        runAllSystems(deltaTime);

    }

    private void runAllSystems(float deltaTime) {
        for (BaseSystem system : systems) {
            if(!system.running()) continue;

            long start = System.nanoTime();

            List<Class<? extends BaseComponent>> signature = system.signature();

            if (signature == null || signature.isEmpty()){
                system.update(this, entities, deltaTime);
            } else {
                system.update(this, systemEntityCache.get(system), deltaTime);
            }

            long ms = (System.nanoTime() - start) / 1000000;
            if (ms > 8) {
                System.out.println(system.getClass().getSimpleName() + " took " + ms + "ms");
            }

        }
    }

    private void updateCacheIfNeeded() {
        boolean entitiesChanged = entities.removeIf(BaseEntity::removed);

        if (!entitiesToAdd.isEmpty()){
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
            entitiesChanged = true;
        }

        if (entitiesChanged || systemEntityCache.isEmpty()){
            for (BaseSystem system : systems){
                List<Class<? extends BaseComponent>> signature = system.signature();

                List<BaseEntity> matching = systemEntityCache.computeIfAbsent(system, _ -> new ArrayList<>());
                matching.clear();

                if (signature != null && !signature.isEmpty()){
                    for (BaseEntity entity : entities){
                        if (entity.hasAll(signature)) matching.add(entity);
                    }
                }
                systemEntityCache.put(system, matching);
            }
        }
    }

    @Override
    public void addEntity(BaseEntity entity){
        entitiesToAdd.add(entity);
    }

    @Override
    public void addSystem(BaseSystem system){
        systems.add(system);
    }

    @Override
    public void removeEntity(BaseEntity entity){
        entities.remove(entity);
    }

    @Override
    public void removeSystem(BaseSystem system){
        systems.remove(system);
    }

    @Override
    public <T extends BaseComponent> boolean hasEntitiesWith(Class<T> requiredComponent) {
        for (BaseEntity entity : entities) {
            if (entity.has(requiredComponent)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<BaseEntity> getEntitiesWith(Class<? extends BaseComponent> componentType) {
        List<BaseEntity> result = new ArrayList<>();
        for (BaseEntity entity : entities) {
            if (entity.hasAll(componentType)){
                result.add(entity);
            }
        }
        return result;
    }

    @SafeVarargs
    @Override
    public final List<BaseEntity> getEntitiesWith(Class<? extends BaseComponent>... componentTypes){
        List<BaseEntity> result = new ArrayList<>();
        for (BaseEntity entity : entities) {
            if (entity.hasAll(componentTypes)){
                result.add(entity);
            }
        }
        return result;
    }

    @Override
    public float getDeltaTime() {
        return deltaTime;
    }

    @Override
    public void clearEntities() {
        entities.clear();
        entitiesToAdd.clear();
    }

    @Override
    public void clearSystems() {
        systems.clear();
    }

    @Override
    public Camera3D getCamera() {
        return camera;
    }

    @Override
    public void setCameraLocation(Vector3D cameraLocation) {
        camera.target(cameraLocation.toVector3(RL_VEC_SCRATCHPAD));
    }

    @Override
    public int getScreenWidth() {
        return screenWidth;
    }

    @Override
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    @Override
    public int getScreenHeight() {
        return screenHeight;
    }

    @Override
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    @Override
    public float getWorldSize() {
        return this.worldSize;
    }
}
