package io.asteroidsjaylib.common.button;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.BaseEvent;

public class ClickedEvent extends BaseEvent {
    public BaseEntity clickedEntity;

    public ClickedEvent(BaseEntity clickedEntity){
        this.clickedEntity = clickedEntity;
    }
}
