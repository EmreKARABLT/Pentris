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
    public static final int lineDeletionMax = 3;
    public static int tick = 500;
    public static int botTick = 500;
    public static int score = 0;
    public static int counter = 0 ;
    static int scoreForMove;
    public static int pieceID;
    public static UI ui = new UI(WIDTH , HEIGHT ,50);
    public static int currentX = 0 ;
    public static int currentY = 0 ;
    public static int currentMutation = 0 ;
    public static int[][] field ;
    public static int[][] piece ;
    public static int[][] prevMut ;
    public static int[][] nextMut ;
    public static ArrayList <Integer> pieceIDS = new ArrayList<Integer>(0);
    public static boolean isDropped = false;
    public static boolean isRotated = false;
    public static ArrayList<Integer> pieces = new ArrayList<Integer>(12);
    //    public static boolean isPlayer = true;
//    public static boolean isBot = false;
    public static boolean isPlayer ;
    public static boolean isBetterBot;
    public static boolean isBot ;
    public static boolean isDumbBot ;
    public static boolean isDumbestBot ;
    public static boolean isGAon = true ;


    public static ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isGameOver){
                t.stop();
            }
            moveBottom(field);
        }
    };
    static Timer t ;
    static KeyListener keys = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_S){
                t.stop();
            }
            if(e.getKeyCode() == KeyEvent.VK_W){
                t.start();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moveLeft(field );
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//
                moveRight(field );
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
                if(!isDropped){
                    instantDrop();
                    isDropped = true ;
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

    public Game(boolean isPlayer ,boolean isBetterBot , boolean isBot , boolean isDumbBot , boolean isDumbestBot){
        this.isPlayer = isPlayer ;
        this.isBetterBot = isBetterBot;
        this.isBot = isBot ;
        this.isDumbBot = isDumbBot;
        this.isDumbestBot = isDumbestBot ;
        field = createAnEmptyGrid(HEIGHT , WIDTH );
//        piecePicker(false);
        placeTopPiece();
        t =new Timer( (isPlayer) ? tick : (isGAon) ? 1 : botTick , al);
        t.start();
    }
    public static int[][] createAnEmptyGrid(int x , int y ){
        int[][] field = new int[x][y] ;
        for (int[] fieldLine: field) {
            Arrays.fill(fieldLine , -1);
        }
        return field;
    }
    public static int[][] piecePicker(boolean firstcall) {
        if (pieces.size()<1){
            for (int i = 0; i<12; i++) pieces.add(i);
        }
        Random ran = new Random();
        int randomInt = ran.nextInt(pieces.size());
        pieceID = pieces.get(randomInt);

//        if (firstcall) pieces.remove(randomInt);
//        currentMutation = 0 ;
//        if (pieceIDS.size() == 0){
//            pieceIDS.add(0, pieceID);
//            piecePicker(true);
//        }
//        else if (pieceIDS.size() == 1){
//            pieceIDS.add(1, pieceID);
//            piecePicker(true);
//        } else if (pieceIDS.size() == 2 && firstcall){
//            pieceIDS.remove(0);
//            pieceIDS.add(1, pieceID);
//            pieceID = pieceIDS.get(0);
//            return PentominoDatabase.data[pieceID][currentMutation];
//        }
//        pieceID = pieceIDS.get(0);
//        counter ++;
//        pieceID = counter % 12 ;
//        System.out.println(pieceID);
        return PentominoDatabase.data[pieceID][currentMutation];
    }
    public static int[][] placeTopPiece()  {
        if(isGameOver) {
            return field;
        }

        piece = piecePicker(true);
        currentX = (WIDTH  - piece[0].length - 1 ) / 2  ;
        currentY = 0;
        scoreForMove = scoreForMove();
        for (int i = 0; i < scoreForMove; i++) {
            deleteLine();
        }
//        System.out.printf("current x = %d current mutation %d\n" , currentX , currentMutation);
        if(!isGameOver && isValidPutPiece(field, piece, currentX, currentY)){

            if(!isPlayer) {

                int[] x_m = new int[2];
                if(isBot)
                    x_m = Bot.pickBestMove();
                if(isBetterBot)
                    x_m = BetterBot.pickBestMove();
                if(isDumbBot)
                    x_m = DumbBot.pickBestMove();

                piece = PentominoDatabase.data[pieceID][x_m[1]];
                currentX = x_m[0];
                addPiece(field , piece , x_m[0] , 0 );
                if(isGAon)
                    instantDrop();
//                int steps = Math.abs(x_m[0] - currentX);
////                remove(field , piece , currentX , currentY);
//                for(int i = 0 ; i < steps ; i++){
//                    if( x_m[0] < currentX){
//                        moveLeft(field);
//                    }
//                    if ( x_m[0] > currentX ){
//                        moveRight(field);
//                    }
//                }
            }

            addPiece(field, piece, currentX, currentY);
//            if(isBot){
//                for(int i = currentMutation ; i < x_m[1]  ; i++){
//
//                    System.out.println("rotation");
//                    rotation(field);
//                    ui.setState(field);
//                }
//                for(int i = 0 ; i <= Math.abs(currentX - x_m[0]) ; i++){
//                    try{
//                        Thread.sleep(100);
//                    }catch (Exception e ){
//
//                    }
//                    if(x_m[0] > currentX) {
//
//                        System.out.println("right");
//                        moveRight(field);
//                    }
//                    if(x_m[0] < currentX) {
//
//                        System.out.println("right");
//                        moveLeft(field);
//                    }
//                    else{
//                        System.out.println("right");
//                        moveRight(field);
//                    }
//                }
//
//            }
            ui.setState(field);
        }else {
            isGameOver = true; // Game over, buddy
//            System.out.println("GAME OVER , score " + score )  ;
            return field ;
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
    public static int[][] addPiece(int[][] grid, int[][] piece, int x, int y) {
        ui.setState(field);
        if(isValidPutPiece(grid , piece , x , y)) {
            for (int i = 0; i < piece.length; i++) {
                for (int j = 0; j < piece[i].length; j++) {
                    if (i + currentY < HEIGHT && j + currentX < WIDTH && piece[i][j] == 1)
                        grid[y + i][x + j] = pieceID;
                }
            }
        }


        return grid;
    }
    public static int[][] remove(int[][] grid, int[][] piece , int x , int y ) {
        for(int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[0].length; j++){
                if(i + y < HEIGHT && j + x  < WIDTH && grid[i + y][j + x] == pieceID && piece[i][j] ==  1)
                    grid[i + y ][j + x] = -1;
            }
        }
        ui.setState(grid);
        return grid;
    }
    public static int[][] moveBottom(int[][] field   ) {
        if(isGameOver){return field;}
        ui.setState(field);
        remove(field , piece  , currentX , currentY);

        if (currentY + piece.length < field.length && isValidPutPiece(field, piece, currentX, currentY + 1 ) ) {
            addPiece(field, piece, currentX , currentY + 1);
            currentY++;
            ui.setState(field);
        }else{
            addPiece(field , piece , currentX , currentY);
            ui.setState(field);
//            piece = piecePicker(false);
            placeTopPiece();
        }
        ui.setState(field);

        return field;
    }
    public static int[][] instantDrop(){
        if(isGameOver){return field;}
        while(currentY < HEIGHT) {
            remove(field, piece , currentX , currentY);
            if (isValidPutPiece(field, piece, currentX, currentY+1) ) {
                addPiece(field, piece, currentX, currentY + 1);
                currentY++;
            } else {
                addPiece(field, piece, currentX, currentY);
//                piece = piecePicker(false);
                placeTopPiece();
                break;
            }

        }
        ui.setState(field);
        return field;
    }
    public static int[][] instantDropBot( int[][] grid , int[][] piece, int x , int y ){
        if(isGameOver){return grid;}
        while(y < HEIGHT) {
            remove(grid, piece , x , y );
            if (isValidPutPiece(grid, piece , x, y+1) ) {
                addPiece(grid, piece, x, y+ 1);
                y++;
//                System.out.println("=============ADDED TO   " + x + "   " + y);
            }else{
                break;
            }
        }
//        System.out.println("=============BOT DROPPED IT TO  " + x + "  " + y);
        return addPiece(grid , piece , x , y);
    }
    public static int[][] moveRight(int[][] field ) {
        if(isGameOver){return field;}
        remove(field , piece , currentX , currentY );
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
        remove(field , piece , currentX , currentY);
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
        remove(field , piece , currentX ,currentY);
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
        ui.setState(field);
        return field;
    }
    public static int scoreForMove(){
        scoreForMove = 0 ;
        for(int i = 0 ; i < HEIGHT ; i++){
            boolean isFilled = true;
            for (int j = 0; j < WIDTH; j++) {
                if (field[i][j] == -1) {
                    isFilled = false;
                    break;
                }

            }
            if( isFilled ){
                scoreForMove++;
                for(int l = 0 ; l < field[0].length ; l++){
                    field[i][l] = -10;
                }
            }
        }
        score+= scoreForMove;
        return scoreForMove;
    }
    public static void deleteLine(){


        int[] emptyRow = new int[WIDTH];
        Arrays.fill(emptyRow ,  -1 );

        outer:
        for(int i = HEIGHT - 1 ; i >=1 ; i-- ){
            if(field[i][0] == -10 ){
                for(int x = i ; x > 0 ; x--){
                    field[x] = field[x - 1];
                }
                field[0] = emptyRow;
                break outer;
            }
        }
        ui.setState(field);


//        outer:
//        while(numberOfCompletedLines > 0 )
//            for(int i = 0 ; i < HEIGHT ; i++){
//                if(field[i][0] == -10){
//                    score++;
//                    System.out.printf("Your score = %d \n" ,score );
//                    while(i>0) {
//                        field[i] = emptyRow;
//                        field[i] = field[i - 1];
//                        i--;
//                    }
//                    field[i] = emptyRow;
//                    numberOfCompletedLines--;
//                    if(numberOfCompletedLines == 0 || i == 0 ){
//                        break outer;
//                    }
//                }
//            }
//
//        ui.setState(field);
    }
    public static int[][] nextMutation(int currentMutation) {
        int[][] pieceToPlace =PentominoDatabase.data[pieceID][currentMutation];
        return pieceToPlace;
    }
    public static void main(String[] args) throws InterruptedException {
        JFrame f = UI.window;
        f.addKeyListener(keys);
        Game g = new Game(true , false , false , false , false );
        // ////// MUSIC ///////
        // String Music = "Pentris.wav";
        // Korobeiniki pentrisMusic = new Korobeiniki();
        // pentrisMusic.pentrisMusic(Music);
        // ///////////////////


    }

}