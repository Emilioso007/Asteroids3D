package io.asteroidsjaylib.common.render;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.render.shapes3d.Base3DShape;

import java.util.*;

public class Render3DComponent extends BaseComponent {

    private final Map<String, List<Base3DShape>> shapeLibrary;
    private String currentState = "";

    public Render3DComponent() {
        this.shapeLibrary = new HashMap<>();
    }

    public List<Base3DShape> getActiveShapes() {
        if (getCurrentState().equalsIgnoreCase("")) {
            List<Base3DShape> allShapes = new ArrayList<>();
            for (List<Base3DShape> shapeSet : shapeLibrary.values()) {
                allShapes.addAll(shapeSet);
            }
            return allShapes;
        }

        return shapeLibrary.getOrDefault(getCurrentState(), Collections.emptyList());
    }

    public void addShape(Base3DShape shape, List<String> states){
        for (String state : states){
            if(!shapeLibrary.containsKey(state)) shapeLibrary.put(state, new ArrayList<>());
            shapeLibrary.get(state).add(shape);
        }
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
