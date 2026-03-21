package io.asteroidsjaylib.common.sound;

import com.raylib.Raylib.*;
import io.asteroidsjaylib.common.ecs.BaseComponent;

import java.io.IOException;

import static com.raylib.Raylib.*;

public class SoundComponent extends BaseComponent {
    public final Sound sound;
    public boolean playing = false;

    public SoundComponent(String path) {
        try{
            Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

            var is = caller.getResourceAsStream("/" + path);
            if (is == null){
                throw new RuntimeException("Could not find sound file: " + path + " in " + caller.getName());
            }

            byte[] data = is.readAllBytes();
            is.close();

            String extension = path.substring(path.lastIndexOf(".")).toLowerCase();

            Wave wave = LoadWaveFromMemory(extension, data, data.length);

            this.sound = LoadSoundFromWave(wave);

            UnloadWave(wave);

        } catch (IOException e){
            throw new RuntimeException("Failed to load sound resource: " + path, e);
        }
    }
}
