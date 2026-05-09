package io.asteroidsjaylib.common.render;

import io.asteroidsjaylib.common.ecs.BaseComponent;

import java.util.*;

public final class Render3D extends BaseComponent {

    private final Map<String, List<Base3DShape>> shapeLibrary;
    private final List<Base3DShape> allShapesCache;
    private boolean allShapesDirty = true;
    private String currentState = "";

    public Render3D() {
        this.shapeLibrary = new HashMap<>();
        this.allShapesCache = new ArrayList<>();
    }

    public List<Base3DShape> getActiveShapes() {
        if (currentState().isEmpty()) {
            if (allShapesDirty) {
                allShapesCache.clear();
                for (List<Base3DShape> shapes : shapeLibrary.values()) {
                    allShapesCache.addAll(shapes);
                }
                allShapesDirty = false;
            }
            return allShapesCache;
        }

        return shapeLibrary.getOrDefault(currentState(), Collections.emptyList());
    }


    public void addShape(Base3DShape shape, List<String> states){
        for (String state : states){
            if(!shapeLibrary.containsKey(state)) shapeLibrary.put(state, new ArrayList<>());
            shapeLibrary.get(state).add(shape);
        }
        allShapesDirty = true;
    }


    public void addShape(Base3DShape shape, String state){
        addShape(shape, List.of(state));
    }

    public void addShape(Base3DShape shape){
        addShape(shape, "");
    }

    public String currentState() {
        return currentState;
    }

    public Render3D currentState(String currentState) {
        this.currentState = currentState;
        return this;
    }
}
