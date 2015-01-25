CS 308: Cell Society Plan
====================

###Introduction

###Overview
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

###Design Considerations

###Team Responsibilities