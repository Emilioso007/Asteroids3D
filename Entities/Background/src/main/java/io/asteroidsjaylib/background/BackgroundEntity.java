package io.asteroidsjaylib.background;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.render.ImageComponent;
import io.asteroidsjaylib.common.render.RenderTag;

public class BackgroundEntity extends BaseEntity {

    public BackgroundEntity(){

        RenderTag renderTag = new RenderTag(0);

        ImageComponent imageComponent = new ImageComponent("stars.png", 1600, 1600);
        renderTag.addRenderComponent(imageComponent, 0);

        this.addComponent(renderTag);
    }

}
