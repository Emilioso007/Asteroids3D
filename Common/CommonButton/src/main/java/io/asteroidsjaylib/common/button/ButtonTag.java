package io.asteroidsjaylib.common.button;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class ButtonTag extends BaseComponent {
    public final String id;
    public ButtonTag(String id){
        this.id = id;
    }
}
