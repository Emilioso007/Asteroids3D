package io.asteroidsjaylib;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.util.ITimeProvider;
import org.springframework.stereotype.Component;

@Component
public class RaylibTimeProvider implements ITimeProvider {
    @Override
    public float getTime() {
        return (float) Raylib.getTime();
    }
}
