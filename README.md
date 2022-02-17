# Rush-Hour--Solver

The Rush Hour AI Solver was implemented as an opportunity to explore the strengths and weaknesses of varying Blind Search algorithms. 
Using the board and rules of the tabletop game, Rush Hour, similar to any simple "Unblock the Block" game, I implemented 3 algorithms 
adhering to the search style of Breadth-First, Depth First , and Iterative Deepening Search.

Game Explanation

Rush Hour is a physical puzzle game in which the player is required to slide a red car out of a grid board. However, the car is blocked 
by a maximum of 12 other vehicles that one must also shuffle around the board to clear the way for the central piece. Each piece is 
locked along an axis on the grid and can only move vertically or horizontally, given its orientation. A regular car takes up 
two spaces, while trucks take up three spots. This logic-based puzzle game can be tricky and requires a human player a few iterations to 
complete the puzzle and free the red car.


The Structure

The overall structure of the code is based on analyzing the board at any given stage in the game and storing  possible legal moves.
Potential moves were added to a List labeled Unseen Configurations. Once those moves were processed, they were removed and added to the
Seen Configurations List as a frame of reference so the same move could not be conducted twice, preventing infinite loops and reducing 
memory and processing complexity. This list took the form of a HashMap so configurations could be accessed in O(1) time.


The Algorithms

Breadth-First Search iterates through every possible legal move at a given depth before proceeding to the next depth. As the algorithm 
processes a move, it transfers it to the Seen HashMap and adds the child nodes to the end of the Unseen list. This ensures the 
algorithm inspects the entire row of nodes before descending to the following depth into the next row of child nodes. 

Instead of iterating through entire rows, Depth-First Search descends down the first path it encounters until it reaches a move that has 
already been seen or a dead end. Upon doing so, it retreats back up its path until it finds another path to pursue. Once a move is processed 
in the Seen list, its Unseen children are placed in the beginning as opposed to the end. 
	
Iterative Deepening Search increments its depth by one every time it reaches the final node in a given depth. Much like DFS, child nodes 
are placed at the beginning on the Unseen list until the max depth is reached. 

Results

By observing the results produced by the program, the respective strengths and weakness of each implemenation became clear. Breadth-First, 
while undeniably the fastest, is the most memory inefficient, requiring the most moves visited. Additionally, the solution it found is also 
the most inefficient, requiring well over thousands of moves to be solved.

IDS, while taking the longest, also proves to require the least amount of memory, also finding the most shortest possible solution. 

DPS, meanwhile, sits in between the other search methods, providing the shortest possible path, taking more time than DPS and requiring 
more memory than IDS.
