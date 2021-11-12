import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;
import java.util.Arrays;

public class Bot extends Game implements ActionListener {
    private int lastX ;
    private int lastY ;
    public Bot() throws InterruptedException {
        super();
    }
    static KeyListener keys = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}
    };



    public static void main(String[] args) throws InterruptedException {
        JFrame f = UI.window;
        //since game is parentclass
        Bot bot = new Bot();
        ////// MUSIC ///////
        String Music = "Pentris.wav";
        Korobeiniki pentrisMusic = new Korobeiniki();
        // pentrisMusic.pentrisMusic(Music);
        //////////////////
        f.addKeyListener(keys);
        t =new Timer(tick , al);
        t.start();

    }

    public static void pickBestMove(){
        int[][] snapshot = createAnEmptyGrid(HEIGHT , WIDTH );

        if(isGameOver){return; }
        ui.setState(snapshot);

        System.out.println(snapshot + " "  + field);
        double max = -9999;
        int best_x = currentX ;
        int final_Y = currentY ;
        int best_m = 0 ;
        System.out.println(PentominoDatabase.data[pieceID].length);
        for(int m = 0 ; m < PentominoDatabase.data[pieceID].length ; m++){
            for( int x = 0 ; x <= WIDTH -  piece[0].length  ; x++){
                System.out.printf("Mutation : %d , x : %d \n" , m , x );
                for(int i = 0 ; i < HEIGHT ; i++){
                    snapshot[i] = field[i].clone();
                }

                snapshot  = addPiece(snapshot , piece , x , 0 );
                snapshot = instantDropBot(snapshot); // dropping into the wrong field
                System.out.println("hit");
                double fit_value = Fitness.calculateFitness(snapshot);
                try {
                     Thread.sleep(1000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }

                ui.setState(snapshot);

                if( fit_value > max){
                    best_x = x ;
                    max = fit_value ;
                    best_m = m ;
                }

            }
        }
        addPiece(field,PentominoDatabase.data[pieceID][best_m] , best_x , 0);
        instantDrop();
        ui.setState(field);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
