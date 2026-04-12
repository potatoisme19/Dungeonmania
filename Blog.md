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

[Merge Request 1](/put/links/here)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

[Briefly explain what design choices you made]

[Merge Request 2](/put/links/here)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

[Briefly explain what design choices you made]

Add all other changes you made in the same format here:

## Task 2) Evolution of Requirements 🔧

> Similarly to your assignment-i blog, your blog entries for task 2 should be a **succinct** overview of your design decisions, and any choices you made. Try not to just recount what you did - instead, you should be telling us **_why_** you made those choices.

[DELETE ONE OF THESE!]

### [Option A]: Sun Stone & More Buildables (20 marks)

[Links to your merge requests](/put/links/here)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

**Design Decisions**

[Briefly explain what design choices you made]

**Assumptions/Other Notes**

[Any other assumptions or notes]

### [Option B]: Logic Switches (30 marks)

[Links to your merge requests](/put/links/here)
<!-- Make sure you actually merge your code into the main branch (Don't leave it as an open merge request)!! -->

**Design Decisions**

[Briefly explain what design choices you made]

**Assumptions/Other Notes**

[Any other assumptions or notes]