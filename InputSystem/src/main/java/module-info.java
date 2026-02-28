import io.asteroidsfx.common.system.SystemECS;

module InputSystem {
    requires Common;
    requires InputComponent;
    requires javafx.graphics;

    provides SystemECS with io.asteroidsfx.inputsystem.InputSystem;
}