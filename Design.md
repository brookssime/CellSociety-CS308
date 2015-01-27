CS 308: Cell Society Plan
====================

###Introduction

###Overview

**Main**
	Sets up the stage and plays the animation.
	
**Cell**
	Each cell in our cell automata system must be assigned some state at all times. Cells will individually need to keep track of this state, though the state will depend on the states of cells around it. Therefore, it will be useful to create a Cell class which creates a new cell object that can be updated. When a grid is created, the Cell class is repeatedly called so all cells needed for that grid are created. Examples of information the cell object will contain about itself include its current state within the context of the game (on, off, burning, burned, etc.) , its size, its shape, and pointers to its neighbors. States of the cell can generally be stored as int values, but it would need to keep track of its neighbors with by using pointers to them inside of an ArrayList. 
	Additionally, the Cell class will contain methods that allow values of the cell to be changed or checked. The Cell class will contain set and get methods so that the state of the Cell can be accessed. This will be used by the Grid class which needs to check the state of each cell in order to visually display its state. There will be an updateValues method. If the Grid class checks for and finds a rules change then the cell will need to update pertaining values. 

**Grid**
	All games that this project will be capable of handling will be of the cell automata format. As such the project is designed to handle specifically these kinds of games. In terms of visualizing the game itself, it is clear that we will need a Grid class that represents all cells in the system and their states. Once the game is running, the user will be able to see specific cells in the grid changing their state. This would be manifested visually by the Grid class changing the cell's color in the grid. 
	The grid would have some size assigned to it which represents the numbers of cells created and in the grid. A game's Grid uses a 2D Cell array to organize every instance of Cell so that each cell in the grid can be called to be updated. Since each cell is contained within the array, the Grid class effectively keeps track of the entire game state. When the user pauses the game or switches the rules, the Grid class would use the array to remember the current states of all cells in the simulation. Grid would have a checkRules method to that the simulation to adapt to changes in the rules during a game. 

**Rules**
	The Rules class stores the current settings of the game. This class can be updated by reading a file containing desired rules settings or the user can change them through the UI. The rules are constantly checked throughout the game to make sure game settings (game type, grid size, etc.) do not need to be changed. 

**ReadFile**
	The ReadFile class uses a Scanner to take in a and read a file in XML format. The file contains information about what type of game is going to be played. If values that affect the rules of the game (grid size, grid shape, frames_per_sec etc.) are read then those are stored in another class. The ReadFile class adds updates the rules that the rest of the classes use to display the simulation. If only a game-type is provided (specific parameter values aren't specified) then default values will be used to create the cell automata simulation.

**UserInterface**
	The UserInterface is another method by which the layout of the game can be controlled. The user has different options to control settings like the pause/unpause, window size, frame rate, and game type. This class, like ReadFile, would also interact with the Rules class, which keeps track of the current rules which have been selected. The user can change these settings by navigating a menu we will create with JavaFX's Button class. Clicking a button will either lead you to a new Scene with a list of further specified game options or will enact some change in the Rules class, based on the option the user chooses.

###User Interface


###Design Details
**Main**
This will set up the stage and play the animation. We can set the stage scene from the group or scene that we get from the grid class.


**Cell**
Each cell will have two int variables denoting its position. This will be used for determining its neighbors. Each cell will have a String or int instance variable to denote its current state. In addition, it will also have a Shape instance variable. Thus when changing the state, we can change both the String variable and the shape color.
As such, it may be helpful to have the Cell class extend a Shape or Group so that they can be added to the Grid.
The Cell class will have a method update which updates both its state and its current color.  

**Grid**
As instance variables, this class will have a String name, an int[][] which determines which cells are neighbors. For example, if {0,1} is an element in the array. Then we know the cell at position (i, j+1) is a neighbor of (i, j).
We will also be using a 2D Cell[][] to store all the cells are in what position. 
This class will have a getNeighbors(Cell) which returns an ArrayList of cells that are its neighbors. 
The Grid will be have a checkRules() function which loops over all cells and determines which changes to make.
The handleUpdate() method will then apply those changes. We can have a method update() which calls both of these functions.
This grid will has have an init method which initializes the instance variables, make the grid, and returns either the scene or the group that corresponds for the group. 
This class may have a subclass that will deal 

**Rules**
We were unsure exactly what this class would be. For now, we plane for Rules to be a class that simply holds all the rules for each specific game.
If we do make a class for each rule, then rules would apply some change to a parameter cell. They could have instance variables of an initial condition.

**ReadFile**
We will most likely format our files so that they tell us which game and rules to use. 
This format will allow us to get and set the various parameters that our methods need such as grid size, what type of grid, what kind of neighborhoods to use. 


**User Interface**
To allow the user to pause and play the animation, we can simply create a Button with a setOnMousePressed(e -> animation.pause()) and similarly for the play Button.
To speed up or slow down the animation, we can use a Button such that when it is pressed it can change the rate perhaps something like setRate(getRate()*1.25)
Stepping forward is the same idea. It's a button that when it is pressed it calls the update() function which checks which rules apply and then applies the changes.\
We can use one function makeUIButton(String, EventHandler) to handle the create of all these buttons.
There should also be a button that opens up a file chooser. From there we can choose a file which would then be parsed by the ReadFile class.


###Design Considerations
One decision the group made was not to include a States class as of now. Had one been made, a States class most likely would've included two ArrayLists. One of which will contains the cells that currently hold the cell and one that holds the cells that will be in that state in the next run. This class would allow you to add to the ArrayList for updating in addition to updating all the cells. As our current method for updating cells appears to work, we decided not to go with this method since creating State classes may add to memory. An advantage is that it creates another object which may be used for other activities. It also gives an easy way to count how many cells are in each state in addition to accessing cells of a certain state.
Overall since it seems not needed for now, we decided not to incorporate into the project but this may change when we start coding.
There was also the possibility of creating an abstract CellAutomata class. Pros of this would it make implementing the specific games noted in the basic implementation easier. However we determined the main differences between the games to be different grids, different neighborhoods, and different rules. 
In order to make our project more flexible, we believe that it would be better not to have an abstract CellAutomata class since it was not needed. We could deal with different grids by having different grid subclasses. Neighborhoods can differ by a parameter and we simply need a way to store the rules. 
This way of organizing the project also allows us to play the various games with different mixture of neighborhoods and games.

###Team Responsibilities
We will divide each section in sprint 2 to a different person. However most coding will probably be done together and thus each section will have contributions from each person. Each person will be expected to review the other member's code.

