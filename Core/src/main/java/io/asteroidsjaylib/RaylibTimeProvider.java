package io.asteroidsjaylib;

import io.asteroidsjaylib.common.util.ITimeProvider;
import org.springframework.stereotype.Component;

import static com.raylib.Raylib.GetTime;

@Component
public class RaylibTimeProvider implements ITimeProvider {
    @Override
    public float getTime() {
        return (float) GetTime();
    }
}
