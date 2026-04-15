package io.asteroidsjaylib.common.crystal;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class CrystalTag extends BaseComponent {
    public int value;
    public CrystalTag(int value){
        this.value = value;
    }
}
