import io.asteroidsfx.asteroidentity.AsteroidProvider;
import io.asteroidsfx.common.EntitySpi;

module AsteroidEntity {
    exports io.asteroidsfx.asteroidentity;
    requires Common;
    requires PositionComponent;
    requires RenderComponent;
    requires javafx.graphics;
    requires VelocityComponent;
    requires AngleComponent;
    requires RotationComponent;
    requires InputComponent;
    requires CircleColliderComponent;
    requires OutOfBoundsComponent;

    provides EntitySpi with AsteroidProvider;
}