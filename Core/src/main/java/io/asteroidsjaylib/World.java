package io.asteroidsjaylib;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.IEventBus;
import io.asteroidsjaylib.common.util.Vector3D;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.SHADER_UNIFORM_VEC3;

public final class World implements IWorld {

    private int width;
    private int height;
    private int screenWidth;
    private int screenHeight;
    private Vector3D cameraShake = new Vector3D();
    private final List<BaseEntity> entities;
    private final List<BaseEntity> entitiesToAdd;
    private final Set<BaseSystem> systems;
    private final IEventBus eventBus;

    private final Raylib.Camera3D camera;

    private float deltaTime;

    private final Map<BaseSystem, List<BaseEntity>> systemEntityCache = new HashMap<>();

    private Raylib.Shader shader;
    private int viewPosLoc;

    public World(){
        this.camera = new Raylib.Camera3D();
        this.camera._position(new Vector3D(0, 0, 2000).toVector3());
        this.camera.target(new Vector3D(0, 0, 0).toVector3());
        this.camera.up(new Vector3D(0, 0, 1).toVector3());
        this.camera.fovy(45f);
        this.camera.projection(Raylib.CAMERA_PERSPECTIVE);

        this.entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        Comparator<BaseSystem> systemComparator =
                Comparator.comparing(BaseSystem::getPriority)
                .thenComparing(system -> system.getClass().getName());
        this.systems = new TreeSet<>(systemComparator);
        eventBus = new EventBus();


        try {
            Path tempDir = Files.createTempDirectory("asteroids_shaders_");

            String vsPath = "/lighting.vs";
            String fsPath = "/lighting.fs";

            String vsFileName = new File(vsPath).getName();
            String fsFileName = new File(fsPath).getName();

            File tempVsFile = new File(tempDir.toFile(), vsFileName);
            File tempFsFile = new File(tempDir.toFile(), fsFileName);

            InputStream vsStream = World.class.getResourceAsStream(vsPath);
            if (vsStream == null) throw new RuntimeException("Could not find .vs");
            Files.copy(vsStream, tempVsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            vsStream.close();

            InputStream fsStream = World.class.getResourceAsStream(fsPath);
            if (fsStream == null) throw new RuntimeException("Could not find .fs");
            Files.copy(fsStream, tempFsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            fsStream.close();

            shader = LoadShader(tempVsFile.getAbsolutePath(), tempFsFile.getAbsolutePath());
            int lightDirLoc = GetShaderLocation(shader, "lightDirection");
            Raylib.Vector3 lightDir = new Raylib.Vector3().x(1).y(-1).z(-1);
            SetShaderValue(shader, lightDirLoc, lightDir, SHADER_UNIFORM_VEC3);
            viewPosLoc = GetShaderLocation(shader, "viewPos");

            tempVsFile.deleteOnExit();
            tempFsFile.deleteOnExit();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

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

        cameraShake = new Vector3D();
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
    public Raylib.Camera3D getCamera() {
        return camera;
    }

    @Override
    public void setCameraLocation(Vector3D cameraLocation) {
        camera.target(cameraLocation.toVector3());
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
    public Raylib.Shader getShader() {
        return shader;
    }

    @Override
    public int getViewPosLoc(){
        return viewPosLoc;
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
