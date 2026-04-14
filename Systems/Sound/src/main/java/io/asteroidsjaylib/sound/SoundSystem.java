package io.asteroidsjaylib.sound;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.sound.SoundComponent;

import java.util.List;

import static com.raylib.Raylib.*;

public class SoundSystem extends IteratingSystem {

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        SoundComponent soundComponent = entity.getComponent(SoundComponent.class);

        if (entity.isToBeRemoved()){
            StopSound(soundComponent.sound);
            return;
        }

        if (soundComponent.playing){
            if(!IsSoundPlaying(soundComponent.sound)){
                PlaySound(soundComponent.sound);
            }
        } else {
            StopSound(soundComponent.sound);
        }

    }

    @Override
    public void start(IWorld world) {
        this.setPriority(99);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(SoundComponent.class);
    }
}
