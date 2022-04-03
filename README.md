------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------
Project 1.1 - Phase 2
Date: 08 december 2021
Authors: Pie de Boer, Emre Karabulut, Agata Oskroba, Liutauras Padimanskas, Samantha Cijntje, Liwia Padowska and Jadon Smith

Overview:
In this repository you will find the code for Project 1.1 - Phase 2 of group 25.  
Besides that the file for the presentation can be found here.
The goal of phase 2 was to recreate a tetris style game with a set of 12 pentomino pieces. 
Furthermore, we had to create a bot which tries to play the so called "Pentris" game as good as possible. 
In addition, we implemented genetic algorithm to optimize bot's performance.  

The "Pentris" game (human input version) is a replica of a well-known game "Tetris" created by Alexey Pajitnov in 1984. It works in the following order:

1) It generates two pieces (the current and the following).
2) It tries to spawn the current piece at the top of the board.
3) > If it is a valid piece to put it puts it on the playing grid and the game goes on.
4) > If it is not it stops the game and prints the obtained score to the scoreboard text file.
5) While the current piece on the board can move down without collision with any already occupied square on a grid, the current piece drops by 1 in y-axis on a set tick.
6) While the piece is falling the player is able to move it sideways and rotate with the help of arrow keys and rotation key "Z".
7) When it detects collision it locks the piece in the current x and current y possition.
8) It replaces the current piece with the following piece and generates a new next piece.
9) It updates next piece field in the GUI and starts again from step 2.

The "Pentris" bot (self-playing version) is a bot made by group 25. It works in the following order:
1) It starts the game.
2) It tries every rotation of a current piece in every possible instant-drop grid position and it finds the best move using the following fitness tests:
- Blocks touching bottom of the field
- Blocks touching the sides of the field
- Holes in the field
- Bumpiness in the field
- Height of the field
- Lines cleared for the move
3) It places the piece in the best position according to the above mentioned fitness tests.
4) Until it is possible to spawn a new piece in the top of the field it starts from the step 2. 

Genetic algorithm (optimizing self-playing version of the game). This algorithm let's us to find out the importance of each fitness test. Pseudocode for this algorithm:
```javascript
GeneticAlgorithm():
INITIALIZE random 1st generation;
FOR (g = 1 to N(gen)) DO
	FOR (i = 0 to N(pop)) DO
		i plays the game: Pentris i(g);
	END FOR;
	FOR (i = 0 to N(pop)) DO
		TOURNAMENT SELECTION: Pair up consecutive pairs;
		Save the fitter one according to the scores from Pentris(g);
	END FOR;  
	FOR (i = 0 to N(fit_pop)) DO
		Pair up fit individuals;
		FOR (i = 0 to 4) DO
		DISCRETE CROSSOVER: Create new individual using i(n) and i(n+1) genes;
		Mutate;
		END FOR;
Move 4 new generated individuals to g+1;
	END FOR;
END FOR;
```
User Instructions:
For further reference besides this README, check the javadoc folder.
To run either the game, see the bots in action or check the top-10 leaderboard. Run the Sigma.java file provided in the src folder.

Play GAME
-> Input username
-> Press the play Button
-> ... have fun!

Run BOTS
-> Pick one option in the combo-box
-> Press the play button

LEADERBOARD
-> Press the leaderboard button on the home-screen.

