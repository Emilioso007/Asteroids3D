= 6. Test <test>

/*
Describe how experimental validation was performed through deployment of the game on top of the component container in a real setting.
Test the system's software-abilities such as dynamic updates using integration and unit test.
*/

== 6.1 Building and Running the Game
To build the game, the mvn install command is used. This compiles all the modules into separate .jar files and puts them into a mods-mvn folder.

The game can now be executed using either the 'mvn exec:exec' command, or by using the 'java -p mods-mvn -m Core/io.asteroidsjaylib.Main' command. The java command is the one to be used by consumers of the game, because it only requires the .jar files, and nothing else.

== 6.2 Requirement Validation
All functional requirements have been implemented. Their existence can be seen in the demo video here: https://youtu.be/oF3Cr7Afvww.

- The X-wing player spacecraft is controlled by the player.

- The Tie-fighter enemy crafts are shooting bullets at the player.

- The asteroids are deadly upon impact, as well as being the source of crystals.

- Bullets are fired by either the player or the enemy.

- Crystals can be collected for points.

- The game handles 3D physics, including position, velocity, acceleration, and rotation.

- The rendering is capable of showing various meshes with various materials, including LOD support.

- The game uses shaders to make the bullets glow and mimic realistic lighting overall.

All of the non-functional requirements are also implemented. The video demonstrates the Moddable requirement by removing various .jar files and still having a functional game, just now with fewer features. The rest of the non-functional requirements are documented in the Implementation section above.

Finally, as detailed in Section 5.5, unit testing (NF09) was used to verify the internal logic of the systems. By using Mockito, the behaviour of the game was tested in isolation without needing to boot up the entire Raylib engine.