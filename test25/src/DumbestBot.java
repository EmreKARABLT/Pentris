import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class DumbestBot extends Game implements ActionListener {
    static boolean testmode = false;
    private static boolean runItInfinitely = true ;
    private static int iteration = 10000 ; 
    private static ArrayList<Integer> scores = new ArrayList<>();
    public DumbestBot() throws InterruptedException {
        super();
        isBot = false ;
        isDumbBot = false ;
        isDumbestBot = true;
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
        // ////// MUSIC ///////
        // String Music = "Pentris.wav";
        // Korobeiniki pentrisMusic = new Korobeiniki();
        // pentrisMusic.pentrisMusic(Music);
        // //////////////////
        if (testmode)Tester.looper(false, false, true);
        else NormalRun.run(false, false, true);
        f.addKeyListener(keys);
        t =new Timer(tick ,al );
        t.start();

    }

    public static void pickBestMove(){
        int[][] snapshot = createAnEmptyGrid(HEIGHT , WIDTH );
 
        ui.setState(snapshot);

//        System.out.println(snapshot + " "  + field);
    
//        System.out.println(PentominoDatabase.data[pieceID].length);
        for(int i = 0 ; i < HEIGHT ; i++){
            snapshot[i] = field[i].clone();
        }
        Random ran = new Random();
        int m = ran.nextInt(PentominoDatabase.data[pieceID].length ) ;
        int x = 0;
        if( PentominoDatabase.data[pieceID][m][0].length < 5 ){
            x = ran.nextInt(WIDTH -  PentominoDatabase.data[pieceID][m][0].length +1 );
        }
        
        if(isValidPutPiece(snapshot ,PentominoDatabase.data[pieceID][m] , x , 0 )) {
            snapshot = addPiece(snapshot, PentominoDatabase.data[pieceID][m], x, 0);
            snapshot = instantDropBot(snapshot, PentominoDatabase.data[pieceID][m], x, 0);
        }
        // dropping into the wrong field
//         System.out.println("hit");
        
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


 
        if(isValidPutPiece(field,PentominoDatabase.data[pieceID][m] , x , 0)){
            addPiece(field,PentominoDatabase.data[pieceID][m] , x , 0);
            instantDropBot(field , PentominoDatabase.data[pieceID][m], x  , 0 );
            // ui.setState(field);
            placeTopPiece();
        }
    

    }



}
