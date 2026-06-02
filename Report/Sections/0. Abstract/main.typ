= Abstract
/*
Describe the problem that the report addresses in context of the game domain.

Outline how the developed game addresses the requirement – its key characteristics and fundamental principles (establishing a solution).
*/

This paper documents the development of Asteroids3D. The main architectural problem was building a game that could be modified without recompilation, requiring loose coupling between the core engine and individual gameplay elements. This was achieved by combining the Java Platform Module System and the Spring framework to dynamically discover and load gameplay features introduced as separate .jar files. Additionally, an Entity Component System and the Jaylib-ffm library were utilized to handle the game logic and 3D rendering. The end result is a fully functional game where modules can be added or removed without recompiling the core engine.