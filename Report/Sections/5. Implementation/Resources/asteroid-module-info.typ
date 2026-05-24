#figure(
    ```java

    module Asteroid {
        requires Common;
        requires CommonAsteroid;
        requires CommonCollision;
        requires CommonCrystal;
        requires CommonEnemy;
        requires CommonLifetime;
        requires CommonPhysics3D;
        requires CommonRender;
        requires CommonSpawn;
        requires spring.beans;
        requires spring.context;

        opens io.asteroidsjaylib.asteroid to spring.core, spring.context, spring.beans;

        uses AsteroidSPI;
        uses CrystalSPI;

        provides AsteroidSPI with AsteroidProvider;
        provides BaseSystem with AsteroidCollisionResponseSystem;
    }

    ```,
    caption: [Asteroid module-info file. Imports are excluded.],
    supplement: [Code Snippet]
) <asteroid-module-info>