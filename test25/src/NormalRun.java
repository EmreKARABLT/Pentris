import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class NormalRun extends Game{
    private static int iteration = 2 ; 

    public NormalRun() throws InterruptedException{
        super();
    }
    public static void run(boolean isBot, boolean isDumbBot, boolean isDumbestBot) throws InterruptedException{
        for(int i = 0 ; i < iteration ; i++ ){
            isGameOver = false;
            score = 0 ; 
        
            if (isBot) {
                Bot bot = new Bot();
            } else if (isDumbBot) {
                DumbBot bot = new DumbBot();
            } else if (isDumbestBot) {
                DumbestBot bot = new DumbestBot();
            }  
 
        }
        System.out.println( score );
    } 
}