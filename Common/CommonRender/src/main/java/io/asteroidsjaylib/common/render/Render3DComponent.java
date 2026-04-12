package io.asteroidsjaylib.common.render;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.render.shapes3d.Base3DShape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Render3DComponent extends BaseComponent {

    public final List<Base3DShape> shapes;
    public final Map<String, Base3DShape> shapeLibrary;
    public String state = "";

    public Render3DComponent() {
        this.shapes = new ArrayList<>();
        this.shapeLibrary = new HashMap<>();
    }
}
