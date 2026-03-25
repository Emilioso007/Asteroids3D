package io.asteroidsjaylib.common.sound;

import com.raylib.Raylib.Wave;
import com.raylib.Raylib.Sound;
import io.asteroidsjaylib.common.ecs.BaseComponent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.raylib.Raylib.LoadSoundFromWave;
import static com.raylib.Raylib.LoadWaveFromMemory;

public class SoundComponent extends BaseComponent {
    private static final Map<SoundDefinition, Wave> cache = new HashMap<>();

    public final Sound sound;
    public boolean playing = false;

    public SoundComponent(String path) {
        try{
            Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            SoundDefinition soundDefinition = new SoundDefinition(path, caller);
            Wave wave;

            if (cache.containsKey(soundDefinition)){
                wave = cache.get(soundDefinition);
            } else {
                var is = caller.getResourceAsStream("/" + path);
                if (is == null){
                    throw new RuntimeException("Could not find sound file: " + path + " in " + caller.getName());
                }

                byte[] data = is.readAllBytes();
                is.close();

                String extension = path.substring(path.lastIndexOf(".")).toLowerCase();

                wave = LoadWaveFromMemory(extension, data, data.length);
                cache.put(soundDefinition, wave);
            }

            this.sound = LoadSoundFromWave(wave);

        } catch (IOException e){
            throw new RuntimeException("Failed to load sound resource: " + path, e);
        }
    }

    private record SoundDefinition(String path, Class<?> caller){}
}
