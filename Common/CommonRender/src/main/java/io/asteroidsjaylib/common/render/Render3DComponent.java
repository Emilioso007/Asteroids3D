package io.asteroidsjaylib.common.render;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.render.shapes3d.Base3DShape;

import java.util.*;

public class Render3DComponent extends BaseComponent {

    private final Map<String, List<Base3DShape>> shapeLibrary;
    private final List<Base3DShape> allShapesCache = new ArrayList<>();
    private boolean allShapesDirty = true;
    private String currentState = "";

    public Render3DComponent() {
        this.shapeLibrary = new HashMap<>();
    }

    public List<Base3DShape> getActiveShapes() {
        if (getCurrentState().isEmpty()) {
            if (allShapesDirty) {
                allShapesCache.clear();
                for (List<Base3DShape> shapes : shapeLibrary.values()) {
                    allShapesCache.addAll(shapes);
                }
                allShapesDirty = false;
            }
            return allShapesCache;
        }

        return shapeLibrary.getOrDefault(getCurrentState(), Collections.emptyList());
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

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}
