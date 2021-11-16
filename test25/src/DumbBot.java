import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class DumbBot extends Game implements ActionListener {
    private static boolean runItInfinitely = true ;
    private static int iteration = 10000 ; 
    private static ArrayList<Integer> scores = new ArrayList<>();
    public DumbBot() throws InterruptedException {
        super();
        isBot = false ;
        isDumbBot = true ;
        isDumbestBot = false;
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
        JFrame f = UI.window;
//        since game is parentclass
        ////// MUSIC ///////
        String Music = "Pentris.wav";
        Korobeiniki pentrisMusic = new Korobeiniki();
        pentrisMusic.pentrisMusic(Music);
        //////////////////
        int counter = 0 ;
        for(int i = 0 ; i < iteration ; i++ ){
            isGameOver = false;
            score = 0 ; 
            DumbBot bot = new DumbBot();
            scores.add(score);
            counter++;
            
        }
        int sum = 0 ;
        for(int i =  0 ; i < iteration ; i++){
            sum += scores.get(i);
        }
        double average = sum / (double)iteration ; 
        System.out.println(counter);
        System.out.println(average);
        f.addKeyListener(keys);
        t =new Timer(tick ,al );
        t.start();

    }

    public static void pickBestMove(){
        int[][] snapshot = createAnEmptyGrid(HEIGHT , WIDTH );
 
        ui.setState(snapshot);

//        System.out.println(snapshot + " "  + field);
        double max = -9999;
        int best_x = 0 ;
        int best_Y = 0 ;
        int best_m = 0 ;
//        System.out.println(PentominoDatabase.data[pieceID].length);
        for(int i = 0 ; i < HEIGHT ; i++){
            snapshot[i] = field[i].clone();
        }
        Random ran = new Random();
        for(int m = 0 ; m < PentominoDatabase.data[pieceID].length ; m++){
            for( int x = 0 ; x <= WIDTH -  PentominoDatabase.data[pieceID][m][0].length   ; x++){        
        
                if(isValidPutPiece(snapshot ,PentominoDatabase.data[pieceID][m] , x , 0 )) {
                    snapshot = addPiece(snapshot, PentominoDatabase.data[pieceID][m], x, 0);
                    snapshot = instantDropBot(snapshot, PentominoDatabase.data[pieceID][m], x, 0);
                }
                double fit_value = Fitness.heightFitness(snapshot);
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }


                if( fit_value > max){
                    best_x = x ;
                    best_m = m ;
                    max = fit_value ;
                }
            }

        }
        if(isValidPutPiece(field,PentominoDatabase.data[pieceID][best_m] , best_x , 0)){
            addPiece(field,PentominoDatabase.data[pieceID][best_m] , best_x , 0);
            instantDropBot(field , PentominoDatabase.data[pieceID][best_m], best_x  , 0 );
            // ui.setState(field);
            placeTopPiece();
        }
    

    }



}
