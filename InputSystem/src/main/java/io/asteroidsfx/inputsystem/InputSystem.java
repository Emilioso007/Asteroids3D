package io.asteroidsfx.inputsystem;

import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.system.IteratingSystemECS;
import io.asteroidsfx.common.system.SystemECS;
import io.asteroidsfx.inputcomponent.InputComponent;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Set;

public class InputSystem extends IteratingSystemECS {

    Set<KeyCode> keysPressed;

    @Override
    public void start(World world) {
        this.keysPressed = world.keysPressed;
    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(InputComponent.class);
    }


    @Override
    public void processEntity(Entity entity, double deltaTime) {
        InputComponent inputComponent = entity.getComponent(InputComponent.class);

        for (KeyCode keyCode : keysPressed){
            if(inputComponent.inputActionHashMap.containsKey(keyCode)){
                inputComponent.inputActionHashMap.get(keyCode).execute(entity, deltaTime);
            }
        }
    }
}