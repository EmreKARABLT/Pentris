import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.KeyEvent;

public class Game extends java.util.Timer {
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    public static boolean isGameOver = false;
    static int tick = 1000;
    static int score = 0;
    public static int pieceID;
    public static UI ui = new UI(WIDTH , HEIGHT , 30);
    public static int currentX = 0 ;
    public static int currentY = 0 ;
    public static int currentMutation = 0 ;
    public static int[][] field ;
    public static int[][] piece ;
    public static ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                if(isGameOver){
                    System.out.println("gameover");
                    t.stop();
                }
            try {
                moveBottom(field);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            ui.setState(field);
        }
    };
    static Timer t ;
    static KeyListener keys = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moveLeft(field , piece );
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//
                moveRight(field , piece );
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                rotation(field);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                t.setDelay(tick/5);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                t.setDelay(tick);
            }
        }
    };

    public Game  () throws InterruptedException {
        field = createAnEmptyGrid(HEIGHT , WIDTH );
        piece = piecePicker();
        placeTopPiece(field );
        System.out.println(field.length  + " " + field[0].length);
    }
    public static int[][] createAnEmptyGrid(int x , int y ){
        int[][] field = new int[x][y] ;
        for (int[] fieldLine: field) {
            Arrays.fill(fieldLine , -1);
        }
        return field;
    }
    public static int[][] piecePicker() {
        Random ran = new Random();
        pieceID = ran.nextInt(12);
        currentMutation = 0 ;
        return PentominoDatabase.data[pieceID][currentMutation];
    }
    public static int[][] placeTopPiece(int[][] field) {
        // I will take what was produced from the piecePicker method
        try{
            Thread.sleep(200);
            deleteTheLines();
        }catch (Exception e){
            e.printStackTrace();
        }
        currentX = (WIDTH/2)-1 ;
        currentY = 0;
        piece = piecePicker();
        if(!isGameOver && isValidPutPiece(field, piece, currentX, currentY)){
            addPiece(field, piece, currentX, currentY);
        }else {
            isGameOver = true; // Game over, buddy
        }
        return field;
    }
    public static boolean isValidPutPiece(int[][] field , int[][] piece  , int x , int y) {
        for(int i = 0; i < piece.length; i++) {
            for (int j = 0 ; j < piece[0].length; j++){
                //It checks if it is out of bounds
                if ( x + piece[0].length > field[0].length || y+piece.length > field.length){
                    return false ;
                }
                //It checks if a square is already occupied

                if(piece[i][j] == 1 && field[y + i ][ x + j ] != -1){
                    return false ;
                }
            }
        }
        return true;
    }
    public static int[][] addPiece(int[][] field, int[][] piece, int x, int y) {

        for(int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[i].length; j++){
                if(piece[i][j] == 1 )
                    field[y + i][x + j] = pieceID;
            }
        }
        ui.setState(field);

        return field;
    }
    public static int[][] remove(int[][] field, int[][] piece ) {
        for(int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[0].length; j++){
                if(field[i + currentY][j + currentX] == pieceID && piece[i][j] ==  1)
                    field[i + currentY ][j + currentX] = -1;
            }
        }
        return field;
    }
    public static int[][] moveBottom(int[][] field   ) throws InterruptedException {
        if(isGameOver){return field;}
        remove(field , piece );
        if (currentY + piece.length < field.length && isValidPutPiece(field, piece, currentX, currentY + 1 ) ) {
            remove(field, piece );
            addPiece(field, piece, currentX , currentY + 1);
            currentY++;
        }else{
            addPiece(field , piece , currentX , currentY);
            piece = piecePicker();
            placeTopPiece(field);
        }
        ui.setState(field);
        return field;
    }
    public static int[][] moveRight(int[][] field , int[][] piece  ) {
        if(isGameOver){return field;}
        remove(field , piece );
        if (currentX + piece[0].length + 1 <= field[0].length && isValidPutPiece(field, piece, currentX +1, currentY ) ) {
            addPiece(field, piece, currentX + 1 , currentY );
            currentX++;

        }else {
            addPiece(field, piece, currentX, currentY);
        }
        ui.setState(field);
        return field;
    }
    public static int[][] moveLeft(int[][] field , int[][] piece  ) {
        if(isGameOver){return field;}
        remove(field , piece );
        if (currentX  >= 1 && isValidPutPiece(field , piece, currentX -1, currentY ) ) {
            addPiece(field, piece, currentX - 1 , currentY );
            currentX--;
            ui.setState(field);
            return field;
        }
        addPiece( field , piece , currentX , currentY);
        ui.setState(field);
        return field;
    }
    public static int[][] rotation(int[][] field){
        if(isGameOver){return field;}
        remove(field , piece );
        int[][] prevMut = piece;
        int[][] nextMut = rotateArrayCW(prevMut);
        if (isValidPutPiece(field , nextMut , currentX , currentY ) ) {
            piece = nextMut;
            addPiece(field,piece,currentX , currentY );
        }else {
            addPiece(field, prevMut, currentX, currentY);
        }
        ui.setState(field);
        return field;
    }
    static int[][] deleteTheLines(){

        int numberOfComLines = 0 ;
        for(int i = 0 ; i < field.length ; i++){
            boolean isFilled = true;
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == -1) {
                    isFilled = false;
                    break;
                }

            }
            if( isFilled ){
                numberOfComLines++;
                score ++;
                System.out.printf("Your score = %d " ,score );
                for(int l = 0 ; l < field[0].length ; l++){
                    field[i][l] = -10;
                }
            }
        }
        while(numberOfComLines > 0 )
            for(int i = 0 ; i < field.length ; i++){
                for(int j = 0 ; j < field[0].length ; j++){
                    if(field[i][j] == -10){
                        int[] emptyRow = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                        while(i >= 1 ){
                            field[i] = emptyRow;
                            field[i] = field[i-1];
                            i--;
                            numberOfComLines--;
                        }
                    }
                }
            }
        return field;
    }

    //////////////!!!!!!!!!!!!!!!!!!!!!! COPIED FROM STACKOVERFLOW !!!!!!!!!!!!!!!!!///////////////////////////
    //REWRITE THE ROTATEARRAYCW
    static int[][] rotateArrayCW(int[][] orig) {
        final int rows = orig.length;
        final int cols = orig[ 0 ].length;

        final int[][] neo = new int[ cols ][ rows ];

        for ( int r = 0; r < rows; r++ ) {
            for ( int c = 0; c < cols; c++ ) {
                neo[ c ][ rows - 1 - r ] = orig[ r ][ c ];
            }
        }

        return neo;
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame f = UI.window;
        Game g = new Game();
        f.addKeyListener(keys);
        t =new Timer(tick , al);
        t.start();
    }

}