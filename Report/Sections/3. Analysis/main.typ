= 3. Analysis <analysis>

The following analysis section describes what the system should do, and what interfaces and entities that could facilitate the requirements stated in section 2. Requirements. This will be documented using various diagrams.


#figure(
    image("Resources/UseCaseDiagram.png"),
    caption: [Use Case Diagram that shows what interactions the player can do in the game, as well as what objective each action can lead to.]
) <figure-1>

Based on this diagram (#ref(<figure-1>)) we can see that the system should consist of a player entity that can do various tasks based on user-input.

UC1: When the player navigates the spacecraft, they might intersect a crystal. If so, they collect it and their score is incremented. A high score is the primary goal of the player.

UC2: When the player navigates the spacecraft, they might intersect an obstacle, be it an asteroid, enemy or bullet. This will result in a crash of the spacecraft and game over.

UC3: When the player fires their weapons, they might hit an obstacle, be it an asteroid or enemy. This will destroy said obstacles clearing the path for the player.

This Use Case analysis confirms the proposed entities from the requirements.
-	The player is the user-controlled spacecraft.
-	The asteroid is an obstacle containing valuable crystals.
-	The enemy is an obstacle firing bullets at the player.
-	The bullet is a fast-flying entity coming from either the player or the enemies.
-	The crystal is the valuable resource.


To link the entities and logic together, some contracts must be provided, namely:

IGamePluginService - Responsible for adding entities to the system.

IEntityProcessorService - Responsible for doing early logic, like movement.

IPostEntityProcessorService - Responsible for doing late logic, like collision detection/resolution.

With these three contracts, it is possible to add entities and perform advanced logic on them.


#figure(
    image("Resources/ObjectModel.png"),
    caption: [Object Model Diagram showing relation between entities and contracts.]
) <figure-2>

On #ref(<figure-2>) you can see how these three contracts would be wired up in the system. Bullets and Crystals are both specific edge cases left out for simplicity. The key takeaway is that entities implement the IGamePluginService, telling the system that they want to be added immediately, meanwhile a physics class would implement the IEntityProcessorService, because it needs to calculate movements as the first thing in the frame. A collision class would then implement the IPostEntityProcessorService, since the collision detection and resolution should be done after any movement.


#figure(
    image("Resources/Sequence.png"),
    caption: [Sequence Diagram showing how Core communicates with the contracts.]
) <figure-3>

On #ref(<figure-3>) you can see how the three contracts play together in the system. At the very beginning, Core calls the start method on all IGamePluginService implementations with the World as argument. The services would then add their respective entities to the world.
Then the main loop start, where the IEntityProcessorService implementations are called first, followed by the IPostEntityProcessorService implementations. Both have a method, process, which takes the world as an argument. Finally, the stop method is invoked, and any remaining entities are removed from the world.
The World would contain a reference to all the entities which the processor would access and manipulate as need be.
