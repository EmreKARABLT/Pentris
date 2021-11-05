/**
 * @author Group 25 ( Pie De Boer , Liwia Padowska , Emre Karabulut , Liutauras Padimanskas , Agata Oskroba , Samantha Cijntje , Jadon Smith )
 * @version 07.10.21
 */

import java.util.ArrayList;


/**
 * This class includes the methods to support the search of a solution.
 */

public class Search {
	//assigning default values for grid size , area and input.
	public static int horizontalGridSize = 5;
	public static int verticalGridSize = 12;
	public static int area = horizontalGridSize * verticalGridSize;
	public static final char[] input = { 'Z','Y','T','I','L','N','X','P','V','U','F','W'};

	public static int[][][][] data = PentominoDatabase.data;
	public static boolean[][] isUsed = new boolean[data.length][];

	//	Static UI class to display the board
	public static UI ui ;

	/**
	 * Helper function which starts the branching algorithm
	 */
	public static void search() {

		int[] gridSize = Input.askForGrid();
		horizontalGridSize = gridSize[0];
		verticalGridSize = gridSize[1];
		area = horizontalGridSize * verticalGridSize ;
		ArrayList<Character> inputList = Input.getLetters(horizontalGridSize , verticalGridSize);
		ui = new UI(horizontalGridSize, verticalGridSize, 50);

		for( int x = 0 ; x < data.length ; x++ ) isUsed[x] = new boolean[data[x].length];

		int[][] field = new int[horizontalGridSize][verticalGridSize];
		for(int i = 0; i < field.length; i++) {
			for(int j = 0; j < field[i].length; j++) {
				// -1 in the state matrix corresponds to empty square
				// Any positive number identifies the ID of the pentomino
				field[i][j] = -1;
			}
		}
		//Start the branching
		if(!puzzleSolver(inputList.size(), field , null , inputList)){
			System.out.println("THERE IS NO POSSIBLE SOLUTION");
		}

	}
	/** Generic algorithm that checks whether the current location on the grid is -1,
	 * if it is, we recursively check its neighbours, if they are also -1, we change the values of -1 to the value of -2,
	 * so that we can count them later
	 *
	 * @param field The current grid to run the floodfill algorithm with
	 * @param x The current position of x
	 * @param y The current position of y
	 * @param elementToReplace The value -1, so the empty spaces
	 * @param newValue The value -2 to replace the -1 with, and which we count in the connectedEmptySpacesCounter method
	 */
	public static void floodFill(int field[][], int x, int y, int elementToReplace, int newValue ){

		// Checks if we are NOT out of bound
		if (x < 0 || x >= horizontalGridSize || y < 0 || y >= verticalGridSize)
			return ;
		//Checks if the position is not equal to elementToReplace which is -1 in this case
		if (field[x][y] != elementToReplace)
			return ;

		// If position has not been visited yet, replace it with newValue -2
		field[x][y] = newValue;

		//We are recursively checking top, bottom, left and right from the starting point looking for -1s
		floodFill(field, x+1, y, elementToReplace, newValue);
		floodFill(field, x-1, y, elementToReplace, newValue);
		floodFill(field, x, y+1, elementToReplace, newValue);
		floodFill(field, x, y-1, elementToReplace, newValue);
	}
	/**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
	private static int characterToID(char character) {
		int pentID = -1;
		if (character == 'X') {
			pentID = 0;
		} else if (character == 'I') {
			pentID = 1;
		} else if (character == 'Z') {
			pentID = 2;
		} else if (character == 'T') {
			pentID = 3;
		} else if (character == 'U') {
			pentID = 4;
		} else if (character == 'V') {
			pentID = 5;
		} else if (character == 'W') {
			pentID = 6;
		} else if (character == 'Y') {
			pentID = 7;
		} else if (character == 'L') {
			pentID = 8;
		} else if (character == 'P') {
			pentID = 9;
		} else if (character == 'N') {
			pentID = 10;
		} else if (character == 'F') {
			pentID = 11;
		}
		return pentID;
	}

	/**
	 * The puzzleSolver method recursively tries to put the given set of pentominoes at different x and y locations. Whenever it encounters a valid solution, it returns it.
	 * @param k the number of elements that can be used on a specific grid size
	 * @param field the grid that we are trying to fill 
	 * @param order	the list of letters that are currently used
	 * @param input	the list of given letters 
	 * @return boolean that represents whether there is a solution 
	 */
	public static boolean puzzleSolver( int k , int[][] field , ArrayList<Character> order , ArrayList<Character> input){
		//On the first method call, a list is created to store the elements that were put on the grid already
		if(order == null) {
			//If the area is not divisible by 5 or the given elements can not fill the specific grid, the method immediately returns false
			if( area % 5 != 0 || input.size() * 5 <  area  ) {
				System.out.println("it cannot be filled");
				return false;
			}
			order = new ArrayList<>();
		}

    //Check if the grid is filled with pentominoes
		if(isAllOccupied(field)) {
			System.out.println("solved");
			System.out.println(order);
			ui.setState(field);
			return true;

		}
	//Here the recursion begins
		else{
			/*Initializing a boolean that will be used to check whether there is any gap with a size not divisible by 5,
			 thus it cannot be filled with any pentominoes combination
			*/
			boolean isThereImpossibleGap = false;
			int areaOfFlood = 0 ;

			for (int x = 0; x < horizontalGridSize && !isThereImpossibleGap; x++) {
				for (int y = 0; y < verticalGridSize; y++) {
					//We are cloning the array to be able to check how many -2 it contains (gaps), as this are put instead of empty spaces (-1) and we do not want to change our original field
					int[][] floodField = cloneArray(field);
					floodFill(floodField, x, y, -1 , -2);
					areaOfFlood = connectedEmptySpacesCounter(floodField);
					if(areaOfFlood % 5 != 0)
						isThereImpossibleGap = true;
				}
			}
			//If there is any reasonable gap to fill, so it is a multiple of 5, then we are searching a solution
			if(!isThereImpossibleGap ){
				//We are looking for first empty space and try to put any pentominos there
				//Choosing (x,y) coordinates to put a pentomino
				for (int x = 0; x < horizontalGridSize; x++) {
					for (int y = 0; y < verticalGridSize; y++) {
						for (int i = 0; i < input.size(); i++) {
							//Piece picked from input
							Character element = input.get(i);
							int pentID = characterToID(input.get(i));
							//Looping over the mutations
							for (int j = 0; j < (PentominoDatabase.data[pentID].length); j++) {
								int[][] pieceToPlace =PentominoDatabase.data[pentID][j];

								//Checks if it is allowed to put the current mutation to the x and y location and is not used yet
								if(isValidPutPiece(field, pieceToPlace , x , y) && !isUsed[pentID][j]){
									//Remove used element from input list and add to the order list, adjust state to true
									input.remove(element);
									order.add(element);
									isUsed[pentID][j] = true;
									//We clone the field, if we might want to return to a non-updated state (for backtracking)
									int[][] prevField = cloneArray(field);
									field = addPiece(field, pieceToPlace, pentID, x, y);

									//Only move forward if the current position is occupied
									if( field[x][y] != -1 && puzzleSolver(input.size(), field, order, input)) {
										return true;
									}
									//If the piece is not useful, set piece state to false and call with previous field/state
									else {
										//Moves a non-useful element from order list to input, so it might be used later
										order.remove(element);
										input.add(element);
										isUsed[pentID][j] = false;
										field = prevField;
									}
								}
							}
						}
					}
				}
			}
		}
		//In case there is no solution
		return false;
	}

	/**The method counts the amount of connected gaps that are accessible in the floodfill algorithm
	 *
	 * @param field The currently running field
	 * @return The amount of gaps that are connected
	 */
	public static int connectedEmptySpacesCounter(int[][] field){
		int counter = 0 ;
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if(field[i][j] == -2)
					counter++;
			}
		}
		return counter;
	}

	/**
	 * Method that creates a clone of the array passed as a parameter
	 * @param src The source array to be cloned
	 * @return A clone of the source array
	 */
	public static int[][] cloneArray(int[][] src) {
		int length = src.length;
		int[][] target = new int[length][src[0].length];
		for (int i = 0; i < length; i++) {
			System.arraycopy(src[i], 0, target[i], 0, src[i].length);
		}
		return target;
	}

	/** Iterates over all positions on the grid and checks whether all are not -1 (thus all occupied)
	 *
	 * @param field The current grid
	 * @return Returns a boolean that is true if all positions are occupied, else false
	 */
	public static boolean isAllOccupied(int[][] field){
		boolean isItAllOccupied = true ;
		for(int i = 0 ; i < field.length ; i++){
			for(int j = 0 ; j < field[0].length ; j++){
				if(field[i][j] == -1){
					isItAllOccupied = false;
				}
			}
		}
		return isItAllOccupied;
	}

	/** Check if it is valid to put a pentomino on the grid on a specific spot depending on x,y position.
	 *
	 * @param field The current grid
	 * @param piece The pentomino to be placed on the grid
	 * @param x The x position on the grid - horizontal
	 * @param y The y position on the grid - vertical
	 * @return Boolean that is true when the pent
	 */
	public static boolean isValidPutPiece(int[][] field , int[][] piece  , int x , int y){
		for(int i = 0; i < piece.length; i++) {
			for (int j = 0 ; j < piece[0].length; j++){
				//It checks if it is out of bounds.
				if ( x + piece.length > field.length || y+piece[0].length > field[0].length){
					return false ;
				}
				//It checks if a square is already occupied
				if(piece[i][j] == 1 && field[x+i][y+j] != -1){
					return false ;
				}

			}
		}
		return true;
	}

	/**
	 * Adds a pentomino to the position (x ,y) on the field (overriding current board at that position)
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 * @param piece a matrix representing the pentomino to be placed in the board
	 * @param pieceID ID of the relevant pentomino
	 * @param x x position of the pentomino
	 * @param y y position of the pentomino
	 * @return  two dimensional updated array
	 */
	public static int[][] addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
		if(isValidPutPiece(field , piece , x , y )){
			for(int i = 0; i < piece.length; i++){
				for (int j = 0; j < piece[i].length; j++){
					if(piece[i][j] == 1 )
						field[x + i][y + j] = pieceID;
				}
			}
		}
		return field;
	}

	/** The method used to remove specific pentomino using it's ID
	 *
	 * @param field The current grid
	 * @param pieceID The ID of the pentomino to be removed
	 * @return The updated field without the pentomino
	 */
	public static int[][] remove(int[][] field, int pieceID) {
		for(int i = 0; i < field.length; i++){
			for (int j = 0; j < field[i].length; j++){
				if(field[i][j] == pieceID )
					field[i][ j] = -1;
			}
		}
		return field;
	}

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
//	public static void main(String[] args) {
//		//Benchmark
//		int iterationTime = 1;
//		long startingTime =  System.currentTimeMillis();
//		for (int t = 0 ; t < iterationTime; t++){
//			search();
//		}
//		long finishingTime =  System.currentTimeMillis();
//		System.out.printf("For %d x %d grid with input size %d. Avarage time is %d miliseconds \n" , horizontalGridSize , verticalGridSize , input.length , (finishingTime - startingTime) / iterationTime);
//
//		//Method call of our search algorithm
//		search();
//
//	}

}