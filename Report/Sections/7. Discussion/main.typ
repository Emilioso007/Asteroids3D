
= 7. Discussion

/*
Discuss how well the game solved the identified essential problems (module updates etc.).
To which extent did your design meet the requirements?
*/

The following section will discuss how well the game solved the main problem of modularity as well as meeting the requirements.

== Solving the Main Problem
Using the Java Platform Module System proved to be an excellent way to allow decoupling and swapping modules in the game. The ServiceLoader API was very easy to utilize, to find implementations matching the common interfaces. The architecture allowed for decoupled and asynchronous development, since dependencies were few.

The Spring IoC Container was a bit of a mouthful at first, but it quickly proved its worth, by making the Game completely decoupled from the ServiceLoader in a very clean way. Just managing a single configuration file made sure that the critical use of the ServiceLoader was not being buried in the game logic.

One critique of this architecture would be that the encapsulation of JPMS and the nature of Spring were clashing a bit, because Spring relies heavily on the Reflection API, while JPMS forbids it. This was solved by using the 'opens ... to ...' declarative, but it felt wrong every time.

Overall it was a pleasure to develop a modular program in this way, and the overhead proved its worth quickly.

== Meeting the Requirements
All of the set requirements were implemented as documented in the previous test section.

Using the ECS architecture made it very easy to add new features and entities to the game, which greatly increased development velocity.

The hardest feature to implement was definitely handling rotation in 3D, which required complex (literally) math in the form of quaternions. Luckily this is a well-documented topic in 3D game programming.

The Score Service Spring Boot Application requirement felt very out of place, but worked out pretty well.