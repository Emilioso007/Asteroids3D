package io.asteroidsfx.inputsystem;

import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import io.asteroidsfx.inputcomponent.InputComponent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashSet;

public class InputSystem extends System{

    HashSet<KeyCode> keysPressed;

    public InputSystem(HashSet<KeyCode> keysPressed){
        this.keysPressed = keysPressed;
    }

    @Override
    public void tick(float dt, ArrayList<Entity> entities) {
        for (Entity entity : entities){
            InputComponent inputComponent = entity.getComponent(InputComponent.class);
            if (inputComponent == null) {
                continue;
            }

            for (KeyCode keyCode : keysPressed){
                if(inputComponent.inputActionHashMap.containsKey(keyCode)){
                    inputComponent.inputActionHashMap.get(keyCode).execute(entity, dt);
                }
            }

        }
    }
}