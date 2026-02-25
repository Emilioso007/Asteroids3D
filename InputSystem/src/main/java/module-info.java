import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.inputsystem.InputSystemProvider;

module InputSystem {
    requires Common;
    requires InputComponent;
    requires javafx.graphics;

    provides SystemSpi with InputSystemProvider;
}