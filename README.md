## COMP2511 Assignment 2 Part 1
# below are the instructions for the assignment 

# 1. Task 1) Code Analysis and Refactoring (30 marks)

In this task, you will need to analyse the design of the monolith, including the patterns and smells present in the implementation, and apply refactoring techniques discussed in the course in order to improve the quality of the code.

1.1. Instructions for Task 1

Put all of your answers to the following theory questions in your blog.

For each refactoring change, before you start coding take some time to plan and write up a design in your blog post. This does not need to be very detailed, and is mainly to help you organise your design. Some things you might want to include:

What fields/methods you will need to add/change in a class
What new classes/packages you will need to create
Why this is the best solution to the problem (is there a smaller/simpler solution that doesn't work?)
If you change your design along the way, make sure to keep the blog up to date!

All your work for each task (e.g. 1a, 1b, 1c) needs to be completed in a separate branch - DO NOT do all your work on the main branch.

After you finish coding, make a Merge Request into the main branch of your repository with the following:

A meaningful MR title that includes the task name (e.g. Task 1C complete)
A brief description which outlines the changes being made
Make sure to keep the MR cohesive - only one subtask should be in each merge request (i.e., Task 1C and Task 1D should be in separate merge requests, but ALL of Task 1C should be in the same merge request)
Make sure all the Continuous Integration checks (regression tests, linting, coverage) remain passing.
Make sure to do this for each part of the question - if you don't you will lose marks. You MUST merge the branch in - we will not mark anything left in an open merge request.

Updates to your blog do not need to go in the relevant branch (but you are free to do so).

# 1.2. a) From DRY to Design Patterns (6 marks)

Make sure to switch to a new branch before starting this task!
Look inside app/src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.
What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.
Using your chosen Design Pattern, refactor the code to remove the repetition.
After you have completed this task, make sure to push your code and make the merge request on GitLab. When you are happy with the changes, make sure to merge the branch in.

# 1.3. b) Inheritance Design (6 marks)

Make sure to switch to a new branch before starting this task!
Currently, there is a flaw in the inheritance structure of collectables. A note from a member of the previous engineering team says:

"I don't understand why some collectable items like Wood and Treasure have durability and do things like apply buffs, especially when they're not being used in battle anyway."

List one design principle that is violated by collectable objects based on the description above. Briefly justify your answer.
Refactor the inheritance structure of the code, and in the process remove the design principle violation you identified.
After you have completed this task, make sure to push your code and make the merge request on GitLab. When you are happy with the changes, make sure to merge the branch in.

# 1.4. c) Open-Closed Goals (6 marks)

Make sure to switch to a new branch before starting this task!
Look inside the goals package at the code provided.

Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?
If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.
After you have completed this task, make sure to push your code and make the merge request on GitLab. When you are happy with the changes, make sure to merge the branch in.

# 1.5. d) Open Refactoring (12 marks)

Make any other refactoring improvements you see fit to the codebase. This can include resolving code smells, violations of design principles, using Design Patterns discussed or any other general improvements to the health and quality of the code.

Some areas and questions you can consider:

Consider violations of the Law of Demeter and the Liskov substitution principle.
Some classes such as Exit contain a number of common empty and unused methods. This might be a symptom of a deeper issue with the inheritance structure.
The current implementation of the crafting system for buildable entities contains a significant amount of hard coding. Think about how you can improve this.
If you run gradle test, you will see a warning about "deprecated" methods in the codebase. What is the significance of this message? What should you, as the programmer, do with the code?

-------------------------------------------------------------------------
## Logical Switches (30 marks)

In this extension, you will implement a logical circuit system for Dungeonmania. This system relies on Logical Entities, which exhibit different behaviour based on whether or not their condition is satisfied. You will need to implement three new entities:

 - Light bulbs cannot be collected, but can be lit up if their logical condition is satisfied. They are always created as a logical entity.
 - A switch door is a door that is opened when its logical condition is satisfied. It is always created as a logical entity. Unlike regular doors, it does not need a key to open. It cannot be opened by any other means. Switch doors are always created as closed.
 - Wires cannot be collected and are not logical entities, but act as a conductor to pass current between switches and logical entities. Any moveable entity can walk onto a wire.

If a switch is activated, it sends a current to any cardinally adjacent wires and logical entities. Current can be conducted through wires and activated switches only, meaning that a logical entity may be activated directly by a switch if cardinally adjacent, or indirectly by a cardinally adjacent current-carrying wire. This allows for the creation of dungeons with logic circuits.

Switches and wires are not logical entities, but are conductors - they activate cardinally adjacent wires and logical entities when they are switched on or conducting. Light bulbs and switch doors can be activated but do not transmit current through themselves.

All switches and logical entities will be created in an inactive state. Wires and logical entities will also remain activated as long as there is a current from a switch running through them - if at some point there were multiple switches powering a component and one of them is turned off, the component will remain activated if there is still current that can reach it and their logical condition is still fulfilled. If all sources of power for a logical component has been switched off or destroyed, then the logical component will be deactivated.

Any other entities do not have conductive or logical behaviours when placed next to a conducting wire or switch.

Logical entities will have one of the following conditions for activation:

OR - the entity will be activated if there is 1 or more cardinally adjacent activated conductor

AND - the entity is activated only if all cardinally adjacent conductors are activated. Additionally, and entities with only one cardinally adjacent conductor will not activate, even if that conductor is powered.

XOR - the entity will be activated if there is 1 and only 1 cardinally adjacent activated conductor

CO_AND - the entity will only be activated if all cardinally adjacent conductors are active, and cardinally adjacent conductors are all activated on the same tick, e.g. if a switch activates two wires that are both cardinally adjacent to a logical entity with the co_and condition, it should be activated. Similarly to and, co_and entities with only one cardinally adjacent conductor will not turn on, regardless of whether that conductor is powered.

Note that all conductor activation should be checked before before logical conditions are computed within the same tick. Activation order of the logical entities is undefined.

In the case that another conductor powers an already activated component, the current is not 'refreshed'. The co_and case should rely on the tick the adjacent conductor is initially powered from a deactivated state.

Entity            JSON Prefix	        Creatable from Dungeon Map?
Light Bulb (off)	light_bulb_off	      Yes
Light Bulb (on)	  light_bulb_on	      No, since light bulbs will always be created off.
Wire	            wire	                Yes
Closed Switch Door	switch_door	        Yes
Open Switch Door	switch_door_open	  No, since switch doors will always be created as closed.
-------------------------------------------------------------------------
# Test Design 
When it comes to writing tests for the new features, you should write functional tests on the controller.

All of the existing tests in the monolith are written in this way, and you can structure your tests similarly.