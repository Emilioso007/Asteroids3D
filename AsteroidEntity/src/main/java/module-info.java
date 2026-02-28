import io.asteroidsfx.asteroidentity.AsteroidProvider;
import io.asteroidsfx.common.EntitySpi;

module AsteroidEntity {
    requires AngleComponent;
    requires Collision;
    requires Common;
    requires OutOfBounds;
    requires PositionComponent;
    requires RenderComponent;
    requires RotationComponent;
    requires VelocityComponent;
    requires javafx.graphics;
    exports io.asteroidsfx.asteroidentity;

    provides EntitySpi with AsteroidProvider;
}