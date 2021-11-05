import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.KeyEvent;

public class Game extends java.util.Timer {
    public static final int HEIGHT = 15;
    public static final int WIDTH = 5;
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
    public static int[][] prevMut ;
    public static int[][] nextMut ;
    public static boolean isDropped = false;
    public static boolean isRotated = false;
    public static ArrayList<Integer> pieces = new ArrayList<Integer>(12);
    public static ActionListener al = new ActionListener() {
        public static int counter = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isGameOver){
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
                moveLeft(field );
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//
                moveRight(field , piece );
            }
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                if(!isRotated){
                    rotation(field);
                    isRotated = true;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                t.setDelay(tick/5);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    if(!isDropped){
                        instantDrop(field);
                        isDropped = true ;
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                t.setDelay(tick);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                isDropped = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                                 isRotated = false;

            }
        }
    };

    public Game  () throws InterruptedException {
        field = createAnEmptyGrid(HEIGHT , WIDTH );
        System.out.println("cX " + currentX + " cY " + currentY);
        piece = piecePicker(false);
        System.out.println("p.l  " + piece.length + " p[0].l  " + piece[0].length);

        placeTopPiece();
    }
    public static int[][] createAnEmptyGrid(int x , int y ){
        int[][] field = new int[x][y] ;
        for (int[] fieldLine: field) {
            Arrays.fill(fieldLine , -1);
        }
        return field;
    }
    public static int[][] piecePicker(boolean firstcall) {
        deleteTheLines();

        if (pieces.size()<1){
            for (int i = 0; i<12; i++) pieces.add(i);
        }
        //System.out.println( Arrays.toString(pieces.toArray()));
        Random ran = new Random();
        int randomInt = ran.nextInt(pieces.size());
        pieceID = pieces.get(randomInt);
        if (firstcall) pieces.remove(randomInt);
        currentMutation = 0 ;
        return PentominoDatabase.data[pieceID][currentMutation];
    }
    public static int[][] placeTopPiece() {
        // I will take what was produced from the piecePicker method

        piece = piecePicker(true);
        currentX = (WIDTH  - piece[0].length - 1 ) / 2  ;
        currentY = 0;
        System.out.printf("currentX = %d , currentY = %d\n" , currentX , currentY);
        System.out.println(pieceID);
        if(!isGameOver && isValidPutPiece(field, piece, currentX, currentY)){
            addPiece(field, piece, currentX, currentY);
        }else {
            System.out.println("GAME OVER");
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
//        if(isValidPutPiece(field , piece , currentX , currentY)) {
            for (int i = 0; i < piece.length; i++) {
                for (int j = 0; j < piece[i].length; j++) {
                    if (i + currentY < HEIGHT && j + currentX < WIDTH && piece[i][j] == 1)
                        field[y + i][x + j] = pieceID;
                }
            }
//        }

        ui.setState(field);
        return field;
    }
    public static int[][] remove(int[][] field, int[][] piece ) {
        for(int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[0].length; j++){
                if(i + currentY < HEIGHT && j + currentX  < WIDTH && field[i + currentY][j + currentX] == pieceID && piece[i][j] ==  1)
                    field[i + currentY ][j + currentX] = -1;
            }
        }
        return field;
    }
    public static int[][] moveBottom(int[][] field   ) throws InterruptedException {
        if(isGameOver){return field;}
        isRotated = true ;
        remove(field , piece );
        if (currentY + piece.length < field.length && isValidPutPiece(field, piece, currentX, currentY + 1 ) ) {
            addPiece(field, piece, currentX , currentY + 1);
            currentY++;
        }else{
            addPiece(field , piece , currentX , currentY);
            piece = piecePicker(false);
            placeTopPiece();
        }
        ui.setState(field);
        isRotated = false;
        return field;
    }
    public static int[][] instantDrop(int[][] field   ) throws InterruptedException {
        if(isGameOver){return field;}
        while(currentY < HEIGHT) {
            remove(field, piece);
            if (isValidPutPiece(field, piece, currentX, currentY+1) ) {
                addPiece(field, piece, currentX, currentY + 1);
                currentY++;
            } else {
                addPiece(field, piece, currentX, currentY);
                piece = piecePicker(false);
                placeTopPiece();
                break;
            }

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
    public static int[][] moveLeft(int[][] field  ) {
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
        int caseNumber;

        prevMut = piece;

        if ( currentMutation == 3){
            currentMutation = 0;
        } else currentMutation++;

        nextMut = nextMutation(currentMutation);
//        remove(field , piece );
        if (pieceID == 1){
            if (currentMutation == 1 || currentMutation == 3){
                caseNumber = 1;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }   else if (currentMutation == 0 || currentMutation == 2){
                if (currentX >=WIDTH-2) currentX = WIDTH-3;
                caseNumber = 2;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }

        } if (pieceID == 7 || pieceID == 8 || pieceID == 10){
            if (currentMutation == 0){
                if (currentX >=WIDTH-2) currentX = WIDTH-4
                         ;
                caseNumber = 3;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }   else if (currentMutation == 1){
                caseNumber = 4;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }   else if (currentMutation == 2){
                caseNumber = 5;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }   else if (currentMutation == 3){
                caseNumber = 6;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }
        } if (pieceID == 4 || pieceID == 9){
            if (currentMutation == 0){
                if (currentX == WIDTH-2) currentX = WIDTH-3;
                caseNumber = 7;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }   else if (currentMutation == 1){
                caseNumber = 8;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }   else if (currentMutation == 2){
                caseNumber = 9;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }   else if (currentMutation == 3){
                caseNumber = 0;
                return rotationPlacer(nextMut, prevMut, caseNumber);
            }

        } {
            caseNumber = 0;
            return rotationPlacer(nextMut, prevMut, caseNumber);
        }
    }
    public static int[][] rotationPlacer (int[][] nextMut, int[][] prevMut, int caseNumber){
//        remove(field , piece );
        int x = currentX ;
        int y = currentY ;
        if (caseNumber == 1){
            currentX+= 2;
            currentY-= 2;
        } if (caseNumber == 2){
            currentX-= 2;
            currentY+= 2;
        } if (caseNumber == 3){
            currentX+= 0;
            currentY+= 1;
        } if (caseNumber == 4){
            currentX+= 1;
            currentY-= 2;
        } if (caseNumber == 5){
            currentX-= 2;
            currentY+= 1;
        } if (caseNumber == 6){
            currentX+= 1;
            currentY-= 0;
        } if (caseNumber == 7){
            currentX+= 0;
            currentY+= 1;
        } if (caseNumber == 8){
            currentX+= 1;
            currentY-= 1;
        } if (caseNumber == 9){
            currentX-= 1;
            currentY+= 0;
        }

        if (currentX<0) currentX = 0;
        if (currentY<0) currentY = 0;
        if (isValidPutPiece(field , nextMut , currentX , currentY ) ) {
            addPiece(field,nextMut,currentX , currentY );
            piece = nextMut;
        } else {
            currentX = x;
            currentY = y;
            piece = prevMut;
            addPiece(field, piece, x, y);
            currentMutation--;
        }
//        ui.setState(field);
        return field;
    }
    static int[][] deleteTheLines(){

        int numberOfComLines = 0 ;
        for(int i = 0 ; i < HEIGHT ; i++){
            boolean isFilled = true;
            for (int j = 0; j < WIDTH; j++) {
                if (field[i][j] == -1) {
                    isFilled = false;
                    break;
                }

            }
            if( isFilled ){
                numberOfComLines++;
                score++;
                System.out.printf("Your score = %d \n" ,score );
                for(int l = 0 ; l < field[0].length ; l++){
                    field[i][l] = -10;
                }
            }
        }
        int[] emptyRow = new int[WIDTH];
        Arrays.fill(emptyRow ,  -1 );
        while(numberOfComLines > 0 )
            for(int i = 0 ; i < HEIGHT ; i++){
                for(int j = 0 ; j < WIDTH ; j++){
                    if(field[i][j] == -10){
                        while(i>0 && numberOfComLines>0){
                            if(i >=1) {
                                System.out.println("woo");
                                field[i] = emptyRow;
                                field[i] = field[i - 1];
                                i--;
                            }
                        }

                        field[i] = emptyRow;
                        numberOfComLines--;
                    }
                }
            }
        ui.setState(field);
        return field;
    }

    static int[][] nextMutation(int currentMutation) {
        int[][] pieceToPlace =PentominoDatabase.data[pieceID][currentMutation];
        return pieceToPlace;
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame f = UI.window;
        Game g = new Game();
        ////// MUSIC ///////
        String Music = "Pentris.wav";
        Korobeiniki pentrisMusic = new Korobeiniki();
        pentrisMusic.pentrisMusic(Music);
        ///////////////////
        f.addKeyListener(keys);
        t =new Timer(tick , al);
        t.start();
    }

}