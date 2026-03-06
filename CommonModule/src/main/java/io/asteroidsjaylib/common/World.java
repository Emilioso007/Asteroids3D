package io.asteroidsjaylib.common;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.EventBus;
import io.asteroidsjaylib.common.ecs.BaseSystem;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import java.io.IOException;
import java.util.*;

public final class World {

    private int width;
    private int height;
    public int screenWidth;
    public int screenHeight;
    public io.asteroidsjaylib.common.util.Vector cameraLocation;
    private final List<BaseEntity> entities;
    private final List<BaseEntity> entitiesToAdd;
    private final Set<BaseSystem> systems;
    private final EventBus eventBus;

    private double deltaTime;

    private Texture stars;

    public World(){
        cameraLocation = new io.asteroidsjaylib.common.util.Vector();
        this.entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        Comparator<BaseSystem> systemComparator =
                Comparator.comparing(BaseSystem::getPriority)
                .thenComparing(system -> system.getClass().getName());
        this.systems = new TreeSet<>(systemComparator);
        eventBus = new EventBus();

        try {
            // 1. Load from the classpath (works inside JARs and IDEs)
            var is = getClass().getResourceAsStream("/stars.png");
            if (is == null) throw new RuntimeException("Could not find stars.png!");

            byte[] data = is.readAllBytes();

            // 2. Load into an Image (RAM) first
            // Note: ".png" tells Raylib how to decode the byte array
            Image img = LoadImageFromMemory(".png", data, data.length);

            // 3. Convert to Texture (VRAM)
            stars = LoadTextureFromImage(img);

            // 4. Clean up the RAM copy
            UnloadImage(img);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EventBus getEventBus(){
        return eventBus;
    }

    public void tick(double deltaTime){
        this.deltaTime = deltaTime;

        eventBus.updateInputBus(this);

        // Enter world-space (camera transform)
        rlPushMatrix();
        rlTranslatef(
            (float)(-cameraLocation.x + screenWidth  / 2.0),
            (float)(-cameraLocation.y + screenHeight / 2.0),
            0
        );

        // Draw tiled background
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                rlPushMatrix();
                rlTranslatef(i * width, j * height, 0);
                try (Rectangle src = new Rectangle().x(0).y(0).width(stars.width()).height(stars.height());
                     Rectangle dst = new Rectangle().x(0).y(0).width(width).height(height);
                     Vector2 origin = new Vector2().x(0).y(0)) {
                    DrawTexturePro(stars, src, dst, origin, 0, WHITE);
                }
                rlPopMatrix();
            }
        }

        // Run all systems in priority order
        for (BaseSystem system : systems) {
            runSystem(system, deltaTime);
        }

        rlPopMatrix();

        // Cleanup
        getEntities().removeIf(BaseEntity::isToBeRemoved);
        getEntities().addAll(entitiesToAdd);
        entitiesToAdd.clear();
    }

    private void runSystem(BaseSystem system, double deltaTime) {
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
