import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Bot_height_based extends Game implements ActionListener {
    static boolean testmode = true;
    Game game ;

    public Bot_height_based(){
        super(false , false , false , true , false );
    }
    static KeyListener keys = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}
    };
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) throws InterruptedException {


    }

    public static int[] pickBestMove(){
        Random ran = new Random();
        int[] bestPlacement = new int[2];
        int[][] snapshot = createAnEmptyGrid(HEIGHT , WIDTH );
        if(isGameOver){
            return bestPlacement ;
        }
        ui.setState(snapshot);
        double fit_value = 0 ;
        double max = -9999;
        int best_x = 0 ;
        int best_m = 0 ;
        for(int m = 0 ; m < PentominoDatabase.data[pieceID].length ; m++){
            for( int x = 0 ; x <= WIDTH -  PentominoDatabase.data[pieceID][m][0].length   ; x++){
                for(int i = 0 ; i < HEIGHT ; i++){

                    snapshot[i] = field[i].clone();
                }

                if(isValidPutPiece(snapshot ,PentominoDatabase.data[pieceID][m] , x , 0 )) {
                    snapshot = addPiece(snapshot, PentominoDatabase.data[pieceID][m], x, 0);
                    snapshot = instantDropBot(snapshot, PentominoDatabase.data[pieceID][m], x, 0);

                }
                fit_value = Fitness.heightFitness(snapshot);

//
                if( fit_value > max){
                    best_x = x ;
                    best_m = m ;
                    max = fit_value ;
                }

            }
        }
        bestPlacement[0] = best_x;
        bestPlacement[1] = best_m;

        return bestPlacement;
    }



}
