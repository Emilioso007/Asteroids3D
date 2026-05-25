
= 2. Requirements <requirements>
The following requirements are needed to achieve both a fun and extensible game.

Functional requirements for the game:
#figure(
    table(
        columns: 3,
        table.header(
            [No.], [Title], [Description]
        ),
        [F01], [Player], [A controllable player entity.],
        [F02], [Enemy], [Enemy entities shooting at the player.],
        [F03], [Asteroid], [Asteroids acting as both obstacles and resources.],
        [F04], [Bullet], [Bullets being shot by both player and enemy.],
        [F05], [Crystal], [Crystals as the valuable collectible earning the player points.],
        [F06], [Physics], [The game handles physics in 3D space.],
        [F07], [Rendering], [The game renders 3D models with various meshes and materials, including LOD support.],
        [F08], [Shader], [The game uses shaders to mimic lighting and reflectiveness.],
    ),
    caption: [Functional Requirements]
) <fr-table>

Non-functional requirements for the game:

#figure(
    table(
        columns: 3,
        table.header(
            [No.], [Title], [Description]
        ),
        [NF01], [Moddable], [The game supports loading, updating or removing various components without recompiling the core game.],
        [NF02], [Modular], [The game uses Java modules to enforce strict encapsulation and prevent tight coupling between components.],
        [NF03], [Contracts], [The interaction between modules happens through well-defined Service Provider Interfaces, rather than concrete implementations.],
        [NF04], [SoC], [The game uses a data-oriented ECS architecture, which allows for a great separation of concern between individual systems.],
        [NF05], [Microservice], [The game provides a microservice for score tally.],
        [NF06], [Raylib], [The game is using Raylib (Jaylib-ffm) for rendering.],
        [NF07], [JPMS], [The game is using JPMS and the ServiceLoader to orchestrate everything.],
        [NF08], [Spring], [The game uses the Spring framework for dependency injection.],
        [NF09], [Test], [Unit testing is used to ensure the inner workings of modules stay coherent despite changes in architecture.],
    ),
    caption: [Non-functional Rerquirements]
) <nfr-table>
