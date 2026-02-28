package io.asteroidsfx.playerentity;

import io.asteroidsfx.accelerationcomponent.AccelerationComponent;
import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.bulletentity.BulletEntity;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseComponent;
import io.asteroidsfx.common.ecs.BaseEntity;
import io.asteroidsfx.common.ecs.IntervalIteratingSystem;
import io.asteroidsfx.common.event.input.KeyDownEvent;
import io.asteroidsfx.common.event.input.KeyJustPressedEvent;
import io.asteroidsfx.common.event.input.KeyJustReleasedEvent;
import io.asteroidsfx.common.util.Vector;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rotationcomponent.RotationComponent;
import io.asteroidsfx.spawn.SpawnEvent;

import java.util.List;

public class PlayerSystem extends IntervalIteratingSystem {

    @Override
    public void start(World world) {
        this.interval = 0.2;
        this.running = false;
        world.getEventBus().subscribe(KeyJustPressedEvent.class, this::keyJustPressed);
        world.getEventBus().subscribe(KeyDownEvent.class, this::keyDown);
        world.getEventBus().subscribe(KeyJustReleasedEvent.class, this::keyJustReleased);
    }

    private void keyJustPressed(KeyJustPressedEvent event){
        BaseEntity player = World.getInstance().getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case SPACE:
                this.running = true;
            break;
            case LEFT:
                player.getComponent(RotationComponent.class).dAngle = -Math.PI;
                break;
            case RIGHT:
                player.getComponent(RotationComponent.class).dAngle = Math.PI;
                break;
            case UP:
                AccelerationComponent acceleration = player.getComponent(AccelerationComponent.class);
                double angle = player.getComponent(AngleComponent.class).angle;
                acceleration.acc.add(Vector.fromAngle(angle).setMag(2500));
                break;
        }
    }

    private void keyDown(KeyDownEvent event){
        BaseEntity player = World.getInstance().getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case UP:
                AccelerationComponent acceleration = player.getComponent(AccelerationComponent.class);
                double angle = player.getComponent(AngleComponent.class).angle;
                acceleration.acc.add(Vector.fromAngle(angle).setMag(2500));
                break;
        }
    }

    private void keyJustReleased(KeyJustReleasedEvent event){
        BaseEntity player = World.getInstance().getEntitiesWith(PlayerTag.class).getFirst();
        switch (event.keyCode){
            case SPACE:
                this.running = false;
                break;
            case LEFT:
            case RIGHT:
                player.getComponent(RotationComponent.class).dAngle = 0;
                break;
        }
    }

    @Override
    public void updateInterval(BaseEntity entity, double deltaTime) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        AngleComponent angle = entity.getComponent(AngleComponent.class);
        BulletEntity bullet = new BulletEntity(position.pos.copy().add(Vector.fromAngle(angle.angle).setMag(60)), Vector.fromAngle(angle.angle).setMag(600));
        SpawnEvent event = new SpawnEvent();
        event.entityToSpawn = bullet;
        World.getInstance().getEventBus().publish(event);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PlayerTag.class);
    }
}

