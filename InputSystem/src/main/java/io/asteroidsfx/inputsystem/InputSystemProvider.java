package io.asteroidsfx.inputsystem;

import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;

public class InputSystemProvider implements SystemSpi {
    @Override
    public void start(World world) {
        world.addSystem(new InputSystem(world.keysPressed));
    }
}
