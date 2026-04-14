# Assignment II Part 1: Blog Template

> Note: This blog is written in Markdown. Please familiarise yourself with the [Markdown syntax](https://www.markdownguide.org/basic-syntax/) to ensure your blog is readable for your marker.

## Task 1) Code Analysis and Refactoring ⚙️

### a) From DRY to Design Patterns (6 marks)

[Link to your merge request](https://gitlab.cse.unsw.edu.au/coursework/COMP2511/26T1/students/z5606467/assignment-ii-part-1/-/merge_requests/2)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

> 1. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

My main focus is on the long logic in move() for the subclasses. 
1) In Merceenary line 124 - 156 (invincible case) and ZombieToast lines 40 - 72 (runAway case) both contain identical logic for calculating flee direction from the player. 
2) In Mercenary lines 102 - 113 (invisible case) and ZombieToast lines 27 - 35 (random case) both contain identical random movement logic.

> 2. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

I will use a stratrgy pattern to handle the movement logic. Strategy pattern is good to store different algorithms that can used based on different conditions. This can de-clutter the conditional logic in move() as I separate the movement logics from the enemy characteristics. It is open to extension, closed to modification as adding a new movement type wont change the existing enemy classes. It also increases reusability as I am reusing logics to different classes. 

> 3. Using your chosen Design Pattern, refactor the code to remove the repetition.

1) create a movement strategy interface 
2) implement different concrete strategies for movement strategy (hostile, random, flee, allied)
3) update existing classes to use movement strategy pattern 

### b) Inheritance Design (6 marks)

[Link to your merge request](https://gitlab.cse.unsw.edu.au/coursework/COMP2511/26T1/students/z5606467/assignment-ii-part-1/-/merge_requests/3)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

> 1. List one design principle that is violated by collectable objects based on the description above. Briefly justify your answer.

Liskov's Substitution principle is violated because subclass wood and treasure implement applyBuff() and getDurability() with a placeholder behaviour, which is false because it should not have this characteristic to begin with.

> 2. Refactor the inheritance structure of the code, and in the process remove the design principle violation you identified.

1) add 2 new interface: BattleItem and DurableItem (both are characteristics of an item, like useable)
2) refactor all inventory items to implement respective needed characteristics 
3) update inventory class to handle new interfaces

Now new items can implement only the roles they need and there is clear intent for each interface. 

### c) Open-Closed Goals (6 marks)

[Link to your merge request](https://gitlab.cse.unsw.edu.au/coursework/COMP2511/26T1/students/z5606467/assignment-ii-part-1/-/merge_requests/4)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

> 1. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

I think it violates open /closed principle because if I have to add a new goal I will have to add a new case statement in the code, which it modifying it. 

> 2. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

I will use a composite pattern where the leaf nodes are the goals (exit, boulders, treasure) and the composite node is the operation (AND/OR) 

### d) Open Refactoring (12 marks)

[Merge Request 1](https://gitlab.cse.unsw.edu.au/coursework/COMP2511/26T1/students/z5606467/assignment-ii-part-1/-/merge_requests/5)

- I separated Entity factory into individual factories to separate the logic because it made sense so them to have single responsibility. I let staticEntityFactory have more logic because it is all simple logic. 
- in zombieToastSpawner.java i left 
game.getEntityFactory().getZombieFactory().spawnZombie(game, this);

because i think getEntityFacotry() and getZombieFactory() both return factory and there is no business logic behind it, so I allowed this chained dot. 
- I created spawn and build interface so the individual factory can choose to implement one or both. 

[Merge Request 2](https://gitlab.cse.unsw.edu.au/coursework/COMP2511/26T1/students/z5606467/assignment-ii-part-1/-/merge_requests/6)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

- I created Overlappable interface so that it is no longer a part of entity since sometimes an entity will not implement it. 
- I create MoveAway interface for the same reason. 
- I created Destroy interface for the same reason. 

[Merge Request 3](https://gitlab.cse.unsw.edu.au/coursework/COMP2511/26T1/students/z5606467/assignment-ii-part-1/-/merge_requests/7)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

- I see that there is build logic in inventory for shield and bow. 
- I made a CraftingStrategy interface and added BowStrategy and ShieldStrategy to store the logic to craft these items. 
- inventory will now directly use .craft 
- this way the CraftingStrategies have single responsibility and other files wont need to handle this logic. 

## Task 2) Evolution of Requirements 🔧

> Similarly to your assignment-i blog, your blog entries for task 2 should be a **succinct** overview of your design decisions, and any choices you made. Try not to just recount what you did - instead, you should be telling us **_why_** you made those choices.

### [Option B]: Logic Switches (30 marks)

[Links to your merge requests](/put/links/here)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

**Design Decisions**

[*INTERFACE SEGREGATION*]
1) Created Logical interface for entities that can be activated (Light Bulb, Switch Door, Logical Bomb)
2) Created Conductor interface for entities that transmit current (Switch, Wire)
3) Separate interfaces ensure entities only implement what they need, following Interface Segregation Principle

[*OBSERVER PATTERN FOR CURRENT PROPAGATION*]
1) Implemented Observer pattern to handle state changes efficiently without scanning all entities every tick
2) ConductingSubject interface for entities that emit current (Switch, Wire)
3) ConductingObserver interface for entities that react to current changes (Wire, Light Bulb, Switch Door, Logical Bomb)
4) Enables propagation only through affected wires rather than BFS over entire map each tick

[*STRATEGY PATTERN FOR LOGIC GATES*]
1) Each logical entity has a LogicStrategy (AND, OR, XOR, CO_AND)
2) Strategies are injected at entity creation based on JSON config
3) Adding new logic types requires only creating new strategy class (Open-Closed Principle)

[*FACTORY PATTERN FOR LOGICAL ENTITY CREATION*]
1) Extended EntityFactory to create logical entities with appropriate logic strategies
2) Bomb has two creation paths: MVP bomb (switch-activated) vs Logical bomb (logic-activated)

[*STATE TRACKING FOR CO_AND*]
1) Each conductor tracks activatedTick to know when it became active
2) CO_AND logic checks that all adjacent conductors became active on the same tick
3) Observer pattern naturally provides tick number with each notification

**Assumptions/Other Notes**

- Switches are considered conductors only when activated (boulder on top)
- Wires are neutral conductors that propagate current but don't evaluate logic
- Light bulbs and switch doors do not conduct current (as per spec)
- Logical bombs are only created with logic field in config; without it, they behave as MVP bombs
- Current propagation happens within the same tick before logical entity evaluation
- Multiple switches can power the same wire without causing duplicate notifications
- CO_AND requires all adjacent conductors to become active on the exact same tick number
- Order of logical entity evaluation is undefined and does not affect outcomes
- Regular doors and floor switches are unaffected by logical system

[*EDGE CASES*]
1) Wires with no adjacent power sources remain inactive
2) Circular wire connections are prevented from infinite propagation
3) Logical entities with no adjacent conductors are always inactive (except OR which requires at least one)
4) CO_AND with only one adjacent conductor never activates
5) Bombs with logic field explode only when condition met, not when picked up
