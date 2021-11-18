import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class Tester extends Game{
    private static int iteration = 5000 ; 
    private static int maxScore = 0 ;
    private static ArrayList<Integer> scores = new ArrayList<>();
    public Tester() throws InterruptedException{
        super();
    }
    public static void looper(boolean isBot, boolean isDumbBot, boolean isDumbestBot, boolean isBetterBot) throws InterruptedException{
        int counter = 0;
        for(int i = 0 ; i < iteration ; i++ ){
            isGameOver = false;
            score = 0 ; 
        
            if (isBot) {
                Bot bot = new Bot();
            } else if (isDumbBot) {
                DumbBot bot = new DumbBot();
            } else if (isDumbestBot) {
                DumbestBot bot = new DumbestBot();
            } else if (isBetterBot) {
                BetterBot bot = new BetterBot();
            }  
            scores.add(score);
            counter++; 
            System.out.println(counter);
            if(score > maxScore) maxScore = score ;
        }
        int sum = 0 ;
        for(int i =  0 ; i < iteration ; i++){
            sum += scores.get(i);
        }
        double average = sum / (double)iteration ;
        System.out.println( maxScore );
        System.out.println(average);
    } 
}

