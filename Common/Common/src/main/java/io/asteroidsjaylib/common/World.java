package io.asteroidsjaylib.common;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.EventBus;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public final class World {

    private int width;
    private int height;
    public int screenWidth;
    public int screenHeight;
    public Vector2D cameraLocation;
    private final List<BaseEntity> entities;
    private final List<BaseEntity> entitiesToAdd;
    private final Set<BaseSystem> systems;
    private final EventBus eventBus;

    private double deltaTime;

    public World(){
        cameraLocation = new Vector2D();
        this.entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        Comparator<BaseSystem> systemComparator =
                Comparator.comparing(BaseSystem::getPriority)
                .thenComparing(system -> system.getClass().getName());
        this.systems = new TreeSet<>(systemComparator);
        eventBus = new EventBus();
    }

    public EventBus getEventBus(){
        return eventBus;
    }

    public void tick(float deltaTime){
        this.deltaTime = deltaTime;

        eventBus.updateInputBus(this);

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


    public boolean addEntity(BaseEntity entity){
        return getEntities().add(entity);
    }

    public boolean addSystem(BaseSystem system){
        return getSystems().add(system);
    }

    public <T extends BaseComponent> boolean hasEntitiesWith(Class<T> requiredComponent) {
        return getEntities().stream().anyMatch(baseEntity -> baseEntity.hasComponent(requiredComponent));
    }

    @SafeVarargs
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

    public void queueAddEntity(BaseEntity entityToSpawn) {
        entitiesToAdd.add(entityToSpawn);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }

    public Set<BaseSystem> getSystems() {
        return systems;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void clearEntities() {
        entities.clear();
        entitiesToAdd.clear();
    }

    public void clearSystems() {
        systems.clear();
    }
}
