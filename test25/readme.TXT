------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------
Project 1.1 - Phase 1
Date: 7 october 2021
Authors: Pie de Boer, Emre Karabulut, Agata Oskroba, Liutauras Padimanskas, Samantha Cijntje, Liwia Padowska and Jadon Smith

Overview In this repository you will find the code for Project 1.1 - Phase 1 of group 25. The document for the presentation of phase 1 can also be found here. The goal of phase 1 was to complete a random search algorithm and to develop our own recursive algorithm to solve the 'pentomino' problem. In this problem the algorithm has to find out if a given set of pentominos completely covers a rectangle of a given size.

The basic search algorithm works based on the random computer-generated mutations of pieces. It works in the following order:

It generates a two-dimensional array of given size filled with "-1"
Takes random variations of the pentominoes and tries to fit them on the grid in a random x and y position.
Checks whether the grid is filled. IF YES > prints out the solution IF NOT > clears the array, fills it with "-1". Tries again from step-2.
The recusrive algorithm is based on recursion, pruning and backtracking. It includes the floodfill concept as well. It works in the following order:

It generates a two-dimensional array of given size filled with "-1"
The puzzleSolver method is called in the main, initializing the creation of the arrayList 'order'
If puzzleSolver evaluates to false, there is no solution
If puzzleSolver evaluates to true, it recursively tries different mutations of the pentominos in the running field
User Instructions For further reference besides this README, check the javadoc folder.

In this repository we have provided the following algorithms

(1) RANDOM SEARCH ALGORITHM This is saved as BasicSearch.java in test25/src (2) RECURSIVE ALGORITHM This is saved as Search.java in test25/src

Extract the provided 25-CodeFirstPhase.zip. To test the different algorithms, run the desired above mentioned .java files. Close the GUI the exit the code.