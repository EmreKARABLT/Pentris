// import java.util.Arrays;
// import java.util.Random;

// import javax.print.attribute.standard.ReferenceUriSchemesSupported;

// public class testing{
// private static final int HEIGHT = 15;
// private static final int WIDTH = 5;
//     public static void main(String[] args) {
//         int rotation = 0;
//         Random ran = new Random();
//         int pieceID = ran.nextInt(12);
//         int [][] currentPiece = piecePicker(pieceID, rotation);
//         rotation = Rotater(pieceID, true, rotation);
//         rotation = Rotater(pieceID, true, rotation);
//         rotation = Rotater(pieceID, true, rotation);
//         rotation = Rotater(pieceID, true, rotation);


//     }

//     public static int[][] piecePicker(int pieceID, int rotation) {
//         //Pick the pentomino to place
//         int[][] pieceToPlace =PentominoDatabase.data[pieceID][rotation];
//         System.out.println(Arrays.deepToString(pieceToPlace));
//         return pieceToPlace;
//     }

//     public static int Rotater(int pieceID, boolean rotating, int rotation) {
//         if (rotating && rotation == 3){ 
//             rotation = 0; 
//         } else if (rotating) rotation++;
//         piecePicker(pieceID, rotation);
//         return rotation;
//     }    

// }