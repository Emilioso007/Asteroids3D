= Conclusion

/*
First summarize the report.
Remember that you are summarizing the report for a reader that has read the introduction and the body of the report already and has a strong sense of key concepts and applied technologies.
Explain the potential impacts of your system in relation to the main issue.
Direct future work directions related to the main issue.
However, this should not be seen as an opportunity to develop new ideas in significant detail and should be clearly linked to the work described in your report.
*/

This report documented the development of the Asteroids3D game, a modern take on the classic Asteroids arcade game built using JPMS, Spring, ECS and Raylib.

The primary goal of this project was to develop a modular game where .jar files could be added or removed without the need of recompiling the core game. This was accomplished using Java modules and the ServiceLoader API, which in turn gave the following advantages:
- Strict Encapsulation: The internal implementation was hidden behind module boundaries.
- Reliable Dependencies: All communication between modules was done through stable contracts.
- Low Coupling: Modules only depended on common contracts, and not each other, allowing modules to be implemented independently.
- High Cohesion: Modules were very specific in their domain, and god classes were avoided.

Future work includes adding more gameplay features and advanced enemies with various attack patterns. Some of the more technical classes, like the ShaderManager, could also do with a refactor to increase further development velocity even more.