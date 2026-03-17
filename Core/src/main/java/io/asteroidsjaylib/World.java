package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.IEventBus;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public final class World implements IWorld {

    private int width;
    private int height;
    private int screenWidth;
    private int screenHeight;
    private Vector2D cameraLocation;
    private final List<BaseEntity> entities;
    private final List<BaseEntity> entitiesToAdd;
    private final Set<BaseSystem> systems;
    private final IEventBus eventBus;

    private double deltaTime;

    public World(){
        setCameraLocation(new Vector2D());
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

        // Run all systems in priority order
        for (BaseSystem system : systems) {
            if(!system.running) continue;
            runSystem(system, deltaTime);
        }

        // Cleanup
        getEntities().removeIf(BaseEntity::isToBeRemoved);
        getEntities().addAll(entitiesToAdd);
        entitiesToAdd.clear();
    }

    private void runSystem(BaseSystem system, float deltaTime) {
        List<Class<? extends BaseComponent>> signature = system.getSignature();

        if (signature == null || signature.isEmpty()) {
            system.update(this, new ArrayList<>(), deltaTime);
            return;
        }

        List<BaseEntity> filteredEntities = new ArrayList<>();
        for (BaseEntity entity : getEntities()) {
            if (matchesSignature(entity, signature)) filteredEntities.add(entity);
        }

        system.update(this, filteredEntities, deltaTime);
    }


    @Override
    public boolean addEntity(BaseEntity entity){
        return getEntities().add(entity);
    }

    @Override
    public boolean addSystem(BaseSystem system){
        return getSystems().add(system);
    }

    @Override
    public <T extends BaseComponent> boolean hasEntitiesWith(Class<T> requiredComponent) {
        return getEntities().stream().anyMatch(baseEntity -> baseEntity.hasComponent(requiredComponent));
    }

    @SafeVarargs
    @Override
    public final <T extends BaseComponent> List<BaseEntity> getEntitiesWith(Class<T>... requiredComponents){
        List<BaseEntity> result = new ArrayList<>();
        for (BaseEntity entity : getEntities()){
            boolean hasAllComponents = true;
            for(Class<? extends BaseComponent> requiredComponent : requiredComponents){
                if (!entity.hasComponent(requiredComponent)) {
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
    public List<BaseEntity> getEntities() {
        return entities;
    }

    @Override
    public Set<BaseSystem> getSystems() {
        return systems;
    }

    @Override
    public double getDeltaTime() {
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
    public Vector2D getCameraLocation() {
        return cameraLocation;
    }

    @Override
    public void setCameraLocation(Vector2D cameraLocation) {
        this.cameraLocation = cameraLocation;
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
