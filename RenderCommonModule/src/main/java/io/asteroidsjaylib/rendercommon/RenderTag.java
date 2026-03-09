package io.asteroidsjaylib.rendercommon;

import io.asteroidsjaylib.common.ecs.BaseComponent;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class RenderTag extends BaseComponent {
    private int zIndex = 0;
    private boolean absolutePosition = false;

    private final Map<Integer, RenderComponent> renderComponents = new TreeMap<>();

    public RenderTag(int zIndex){
        this(zIndex, false);
    }
    public RenderTag(int zIndex, boolean absolutePosition){
        this.setzIndex(zIndex);
        this.absolutePosition = absolutePosition;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public boolean isAbsolutePosition() {
        return absolutePosition;
    }

    /**
     * Adds a RenderComponent to the entity.
     * @param renderComponent the RenderComponent to add.
     * @param zIndex defines the order in which the components are rendered on a per-entity basis.
     */
    public void addRenderComponent(RenderComponent renderComponent, int zIndex){
        renderComponents.putIfAbsent(zIndex, renderComponent);
    }

    public Collection<RenderComponent> getRenderComponents() {
        return renderComponents.values();
    }

    public boolean hasRenderComponent(Class<?> renderComponentType){
        for (BaseComponent component : renderComponents.values()){
            if (renderComponentType.isAssignableFrom(component.getClass())){
                return true;
            }
        }
        return false;
    }

    public <T extends RenderComponent> T getRenderComponent(Class<T> renderComponentClass) {

        for (RenderComponent rc : renderComponents.values()) {
            if (renderComponentClass.isAssignableFrom(rc.getClass())) {
                return renderComponentClass.cast(rc);
            }
        }

        return null;
    }
}
