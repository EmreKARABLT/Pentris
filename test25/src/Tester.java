import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class Tester extends Game{
    private static int maxScore = 0 ;
    public static ArrayList<Integer> scores = new ArrayList<>();
    public static void looper( int iteration , boolean isBetterBot, boolean isBot, boolean isDumbBot, boolean isDumbestBot) {
        int counter = 0;
        for (int i = 0; i < iteration; i++) {
            isGameOver = false;
            score = 0 ;
            Bot bot = new Bot();

            int count = 0 ;
            while(!isGameOver){
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            scores.add(score);
            if(score > maxScore) maxScore = score ;
        }
        int sum = 0 ;
        for(int i =  0 ; i < iteration ; i++){
            sum += scores.get(i);
        }
        double average = sum / (double)iteration ;
//        System.out.println("Max score  = " + maxScore );
//        System.out.printf("Average score after %f iterations = %d \n",average , iteration);
    }

}

