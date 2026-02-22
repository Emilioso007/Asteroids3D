module PlayerEntity {
    exports io.asteroidsfx.playerentity;
    requires Common;
    requires javafx.graphics;
    requires InputComponent;
    requires PositionComponent;
    requires AngleComponent;
    requires RenderComponent;
    requires LinearVelocityComponent;
    requires LinearAccelerationComponent;
    requires DragComponent;
    requires WraparoundSystem;
    requires CircleColliderComponent;
}