package io.asteroidsjaylib.common.render;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.render.shapes3d.Base3DShape;

import java.util.ArrayList;
import java.util.List;

public class Render3DComponent extends BaseComponent {

    public final List<Base3DShape> shapes;

    public Render3DComponent() {
        this.shapes = new ArrayList<>();
    }
}
