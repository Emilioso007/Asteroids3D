package io.asteroidsjaylib;

import com.raylib.Raylib.*;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.IEventBus;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.*;

import static com.raylib.Raylib.CAMERA_PERSPECTIVE;

public final class World implements IWorld {

    private static final Vector3 RL_VEC_SCRATCHPAD = new Vector3();
    private int width;
    private int height;
    private int screenWidth;
    private int screenHeight;
    private Vector3D cameraShake = new Vector3D();
    private final List<BaseEntity> entities;
    private final List<BaseEntity> entitiesToAdd;
    private final Set<BaseSystem> systems;
    private final IEventBus eventBus;

    private final Camera3D camera;

    private float deltaTime;

    private final Map<BaseSystem, List<BaseEntity>> systemEntityCache = new HashMap<>();

    public World(){
        this.camera = new Camera3D();
        this.camera._position(new Vector3D(0, 0, 2000).toVector3(RL_VEC_SCRATCHPAD));
        this.camera.target(new Vector3D(0, 0, 0).toVector3(RL_VEC_SCRATCHPAD));
        this.camera.up(new Vector3D(0, 0, 1).toVector3(RL_VEC_SCRATCHPAD));
        this.camera.fovy(45f);
        this.camera.projection(CAMERA_PERSPECTIVE);

        this.entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        Comparator<BaseSystem> systemComparator =
                Comparator.comparing(BaseSystem::getPriority)
                .thenComparing(system -> system.getClass().getName());
        this.systems = new TreeSet<>(systemComparator);
        eventBus = new EventBus();
    }

    @Override
    public IEventBus getEventBus(){
        return eventBus;
    }

    @Override
    public void tick(float deltaTime){
        this.deltaTime = deltaTime;

        boolean entitiesChanged = false;
        if (entities.removeIf(BaseEntity::isToBeRemoved)) entitiesChanged = true;
        if (!entitiesToAdd.isEmpty()){
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
            entitiesChanged = true;
        }

        if (entitiesChanged || systemEntityCache.isEmpty()){
            for (BaseSystem system : systems){
                List<Class<? extends BaseComponent>> signature = system.getSignature();
                List<BaseEntity> matching = new ArrayList<>();
                if (signature != null && !signature.isEmpty()){
                    for (BaseEntity entity : entities){
                        if (matchesSignature(entity, signature)) matching.add(entity);
                    }
                }
                systemEntityCache.put(system, matching);
            }
        }

        // Run all systems in priority order
        for (BaseSystem system : systems) {
            if(!system.running) continue;
            List<Class<? extends BaseComponent>> signature = system.getSignature();

            if (signature == null || signature.isEmpty()){
                system.update(this, entities, deltaTime);
            } else {
                system.update(this, systemEntityCache.get(system), deltaTime);
            }
        }

        cameraShake.mult(0);
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
        return entities.stream().anyMatch(baseEntity -> baseEntity.hasComponents(requiredComponent));
    }

    @SafeVarargs
    @Override
    public final List<BaseEntity> getEntitiesWith(Class<? extends BaseComponent>... requiredComponents){
        List<BaseEntity> result = new ArrayList<>();
        for (BaseEntity entity : entities){
            boolean hasAllComponents = true;
            for(Class<? extends BaseComponent> requiredComponent : requiredComponents){
                if (!entity.hasComponents(requiredComponent)) {
                    hasAllComponents = false;
                    break;
                }
            }
            if (hasAllComponents) result.add(entity);
        }
        return result;
    }

    private boolean matchesSignature(BaseEntity entity, List<Class<? extends BaseComponent>> signature){
        for(Class<? extends BaseComponent> requiredType : signature){
            boolean hasComponent = false;

            for (BaseComponent c : entity.getComponents()){
                if (requiredType.isAssignableFrom(c.getClass())){
                    hasComponent = true;
                    break;
                }
            }

            if(!hasComponent) return false;
        }
        return true;
    }

    @Override
    public void queueAddEntity(BaseEntity entityToSpawn) {
        entitiesToAdd.add(entityToSpawn);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public float getDeltaTime() {
        return deltaTime;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
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
    public void shakeCamera(Vector3D shakeVector){
        cameraShake.add(shakeVector);
    }

    @Override
    public Vector3D getCameraShake(){
        return cameraShake;
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
}
