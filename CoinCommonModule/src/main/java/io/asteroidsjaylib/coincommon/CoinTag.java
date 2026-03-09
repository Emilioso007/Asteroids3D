package io.asteroidsjaylib.coincommon;

import io.asteroidsjaylib.common.ecs.BaseComponent;

public class CoinTag extends BaseComponent {
    public int value;
    public CoinTag(int value){
        this.value = value;
    }
}
