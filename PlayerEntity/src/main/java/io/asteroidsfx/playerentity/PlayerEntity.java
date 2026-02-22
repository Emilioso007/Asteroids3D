package io.asteroidsfx.playerentity;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.circlecollidercomponent.CircleColliderComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.Polygon;
import io.asteroidsfx.dragcomponent.DragComponent;
import io.asteroidsfx.inputcomponent.InputComponent;
import io.asteroidsfx.linearaccelerationcomponent.LinearAccelerationComponent;
import io.asteroidsfx.linearvelocitycomponent.LinearVelocityComponent;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import io.asteroidsfx.wraparoundsystem.WrapComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class PlayerEntity extends Entity {

    public PlayerEntity(int startX, int startY){

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.x = startX;
        positionComponent.y = startY;
        this.components.add(positionComponent);


        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;
        this.components.add(angleComponent);


        LinearVelocityComponent linearVelocityComponent = new LinearVelocityComponent();
        this.components.add(linearVelocityComponent);


        LinearAccelerationComponent linearAccelerationComponent = new LinearAccelerationComponent();
        this.components.add(linearAccelerationComponent);


        DragComponent dragComponent = new DragComponent();
        dragComponent.drag = 5;
        this.components.add(dragComponent);


        RenderComponent renderComponent = new RenderComponent();

        double[] xs = new double[3];
        double[] ys = new double[3];

        for(int i = 0; i < 3; i++){
            xs[i] = Math.cos(Math.toRadians(i*360f/3))*40;
            ys[i] = Math.sin(Math.toRadians(i*360f/3))*40;
        }
        xs[0] = 60;

        renderComponent.polygon = new Polygon(xs, ys, Color.BLUE, Color.LIGHTBLUE, 4);

        this.components.add(renderComponent);


        WrapComponent wrapComponent = new WrapComponent();
        wrapComponent.wrapOutside = true;
        this.components.add(wrapComponent);


        InputComponent inputComponent = new InputComponent();
        inputComponent.inputActionHashMap = new HashMap<>();

        // When LEFT is pressed, rotate counterclockwise
        inputComponent.inputActionHashMap.put(KeyCode.LEFT, (entity, dt) -> {
            AngleComponent angle = entity.getComponent(AngleComponent.class);
            if (angle != null) {
                angle.angle -= Math.toRadians(180) * dt; // rotate left
            }
        });

        // When RIGHT is pressed, rotate clockwise
        inputComponent.inputActionHashMap.put(KeyCode.RIGHT, (entity, dt) -> {
            AngleComponent angle = entity.getComponent(AngleComponent.class);
            if (angle != null) {
                angle.angle += Math.toRadians(180) * dt; // rotate left
            }
        });

        // When UP is pressed, add forward thrust
        inputComponent.inputActionHashMap.put(KeyCode.UP, (entity, dt) -> {
            LinearAccelerationComponent acceleration = entity.getComponent(LinearAccelerationComponent.class);
            if(acceleration != null){
                acceleration.acceleration += 0.25;
            }
        });

        this.components.add(inputComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 50;

        this.components.add(circleColliderComponent);

    }

}