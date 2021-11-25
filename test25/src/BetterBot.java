//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.util.ArrayList;
//
//public class BetterBot extends Game implements ActionListener {
//    static boolean testmode = true;
//    public BetterBot() throws InterruptedException {
//
//        super();
//        isBot = false ;
//        isDumbBot = false ;
//        isDumbestBot = false;
//        isBetterBot = true;
//    }
//    static KeyListener keys = new KeyListener() {
//        @Override
//        public void keyTyped(KeyEvent e) {}
//        @Override
//        public void keyPressed(KeyEvent e) {}
//        @Override
//        public void keyReleased(KeyEvent e) {}
//    };
//        @Override
//        public void actionPerformed(ActionEvent e) {
//        }
//
//    public static void main(String[] args) throws InterruptedException {
//        JFrame f = UI.window;
////        since game is parentclass
//        // ////// MUSIC ///////
//        // String Music = "Pentris.wav";
//        // Korobeiniki pentrisMusic = new Korobeiniki();
//        // pentrisMusic.pentrisMusic(Music);
//        // //////////////////
//        if (testmode)Tester.looper(false, false, false, true);
//        else NormalRun.run(false, false, false, true);
//        f.addKeyListener(keys);
//        t =new Timer(tick ,al );
//        t.start();
//
//    }
//
//    public static void pickBestMove(){
//        int[][] snapshot = createAnEmptyGrid(HEIGHT , WIDTH );
//
//        if(isGameOver){return; }
//        ui.setState(snapshot);
//
////        System.out.println(snapshot + " "  + field);
//        double max = -9999;
//        int best_x = 0 ;
//        int best_Y = 0 ;
//        int best_m = 0 ;
////        System.out.println(PentominoDatabase.data[pieceID].length);
//        for(int m = 0 ; m < PentominoDatabase.data[pieceID].length ; m++){
//            for( int x = 0 ; x <= WIDTH -  PentominoDatabase.data[pieceID][m][0].length   ; x++){
////                System.out.printf("Mutation : %d , x : %d \n" , m , x );
//                for(int i = 0 ; i < HEIGHT ; i++){
//
//                    snapshot[i] = field[i].clone();
//                }
//                if(isValidPutPiece(snapshot ,PentominoDatabase.data[pieceID][m] , x , 0 )) {
//                    snapshot = addPiece(snapshot, PentominoDatabase.data[pieceID][m], x, 0);
//                    snapshot = instantDropBot(snapshot, PentominoDatabase.data[pieceID][m], x, 0);
//                }
//                 // dropping into the wrong field
////                System.out.println("hit");
//                double fit_value = Fitness.calculateOtherFitness(snapshot);
//                try {
//                     Thread.sleep(0);
//                } catch (InterruptedException e) {
//                        e.printStackTrace();
//                }
//
//
//                if( fit_value > max){
//                    best_x = x ;
//                    best_m = m ;
//                    max = fit_value ;
//                }
//
//            }
//            // ui.setState(snapshot);
//        }
//        if(isValidPutPiece(field,PentominoDatabase.data[pieceID][best_m] , best_x , 0)){
//            addPiece(field,PentominoDatabase.data[pieceID][best_m] , best_x , 0);
//            instantDropBot(field , PentominoDatabase.data[pieceID][best_m], best_x  , 0 );
//            // ui.setState(field);
//            placeTopPiece();
//        }
//
//    }
//
//
//
//
//
//}
