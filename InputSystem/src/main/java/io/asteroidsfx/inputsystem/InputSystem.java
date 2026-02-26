package io.asteroidsfx.inputsystem;

import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.inputcomponent.InputComponent;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Set;

public class InputSystem extends System{

    Set<KeyCode> keysPressed;

    public InputSystem(Set<KeyCode> keysPressed){
        this.keysPressed = keysPressed;
    }

    @Override
    public List<Class<? extends Component>> getSignature() {
        return List.of(InputComponent.class);
    }

    @Override
    public void tick(float dt, List<Entity> entities) {
        for (Entity entity : entities){
            InputComponent inputComponent = entity.getComponent(InputComponent.class);

            for (KeyCode keyCode : keysPressed){
                if(inputComponent.inputActionHashMap.containsKey(keyCode)){
                    inputComponent.inputActionHashMap.get(keyCode).execute(entity, dt);
                }
            }

        }
    }
}