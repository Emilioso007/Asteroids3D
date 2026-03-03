package io.asteroidsfx.common;

import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.event.EventBus;
import io.asteroidsfx.common.ecs.BaseSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.*;

public final class World {

    private int width;
    private int height;
    public int screenWidth;
    public int screenHeight;
    public io.asteroidsfx.common.util.Vector cameraLocation;
    private GraphicsContext graphicsContext;
    private final List<BaseEntity> entities;
    private final List<BaseEntity> entitiesToAdd;
    private final Set<BaseSystem> systems;
    private final EventBus eventBus;

    private double deltaTime;

    private final Image stars;

    public World(){
        cameraLocation = new io.asteroidsfx.common.util.Vector();
        this.entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        Comparator<BaseSystem> systemComparator =
                Comparator.comparing(BaseSystem::getPriority)
                .thenComparingInt(BaseSystem::hashCode);
        this.systems = new TreeSet<>(systemComparator);
        eventBus = new EventBus();

        stars = new Image("stars.png");
    }

    public EventBus getEventBus(){
        return eventBus;
    }

    public void tick(double deltaTime){
        this.deltaTime = deltaTime;

        graphicsContext.save();
        graphicsContext.translate(-cameraLocation.x + screenWidth/2d, -cameraLocation.y + screenHeight/2d);

        // Background image
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                graphicsContext.save();
                graphicsContext.translate(i * width, j * height);
                graphicsContext.drawImage(stars, 0, 0);
                graphicsContext.restore();
            }
        }

        for(BaseSystem system : getSystems()){
            List<Class<? extends BaseComponent>> signature = system.getSignature();

            if(signature == null || signature.isEmpty()){
                system.update(this, new ArrayList<>(), deltaTime);
                continue;
            }

            List<BaseEntity> filteredEntities = new ArrayList<>();

            for(BaseEntity entity : getEntities()){
                if(matchesSignature(entity, signature)) filteredEntities.add(entity);
            }

            system.update(this, filteredEntities, deltaTime);
        }

        graphicsContext.restore();

        getEntities().removeIf(BaseEntity::isToBeRemoved);
        getEntities().addAll(entitiesToAdd);
        entitiesToAdd.clear();
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

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
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

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void clearEntities() {
        entities.clear();
        entitiesToAdd.clear();
    }

    public void clearSystems() {
        systems.clear();
    }
}
