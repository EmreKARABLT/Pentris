import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Input {
    public static ArrayList<Character> fromUser = new ArrayList<Character>();
    public static ArrayList<Character> possible = new ArrayList<Character>(Arrays.asList('Z', 'Y', 'T', 'I', 'L', 'N', 'X', 'P', 'V', 'U', 'F', 'W'));

    /**
     *  It takes elements from user for
     * @param x is the length of the given grid
     * @param y is the width of the given grid
     * @return a set of characters
     */
    public static ArrayList<Character> getLetters( int x , int y) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int num = 0;
        //only if number of elements is right for the grid we can proceed
        while (!validInput) {
            System.out.println("How many elements you want to add?");
            num = scanner.nextInt();
            if (num >= (x * y) / 5) {
                validInput = true;
            } else {
                System.out.println("Too little elements");
            }
        }
        System.out.println("What are your elements?");
        for (int i = 0; i < num; i++) {
            char element = scanner.next().charAt(0);
            element = Character.toUpperCase(element);
            if (possible.contains(element) && !(fromUser.contains(element))) {
                fromUser.add(element);
            } else if (!possible.contains(element)) {
                System.out.println("There is no such pentomino, sorry.");
                i--;
            } else {
                System.out.println("No duplicates allowed!");
                i--;
            }
            System.out.println("Current elements " + fromUser);
        }
        System.out.println(fromUser);
        return fromUser;
    }

    public static int[] askForGrid() {
        Scanner scanner = new Scanner(System.in);

        int[] grid = new int[2];
        System.out.println("What is the length of your grid?");
        grid[0] = scanner.nextInt();
        System.out.println("What is the width of your grid?");
        grid[1] = scanner.nextInt();
        return grid;
    }
}