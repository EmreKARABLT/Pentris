import java.util.ArrayList;

public class Tester extends Game{
    public static int maxScore = 0 ;
    public static ArrayList<Integer> scores = new ArrayList<>();
    public static double average;

    public Tester(boolean isPlayer, boolean isBetterBot, boolean isBot, boolean isDumbBot, boolean isDumbestBot) {
        super(isPlayer, isBetterBot, isBot, isDumbBot, isDumbestBot);
    }

    public static void looper( int iteration , boolean isBetterBot, boolean isBot, boolean isDumbBot, boolean isDumbestBot) {
        int min = 2000 ;
        maxScore = 0 ;
        int[] temp_scores = new int[iteration];
        for (int i = 0; i < iteration; i++) {
            isGameOver = false;
            score = 0 ;
            Bot_smart botSmart;
            Bot_6_factors sixfactorsbot;
            Bot_height_based botHeightbased;
            Bot_brute_force botBruteforce;

            if (isBetterBot) sixfactorsbot = new Bot_6_factors();
            if (isBot) botSmart = new Bot_smart();
            if (isDumbBot) botHeightbased = new Bot_height_based();
            if (isDumbestBot) botBruteforce = new Bot_brute_force();


            while(!isGameOver){
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            temp_scores[i] = score ;
            if(score > maxScore) maxScore = score ;

            if(min < temp_scores[i]){
                GeneticAlgorithm.min = temp_scores[i];
            }
        }
            int sum = 0 ;
            for(int i =  0 ; i < iteration ; i++){
                sum += temp_scores[i];
            }
            average = sum / (double)iteration ;
//        System.out.println("Max score  = " + maxScore );
//        System.out.printf("Average score after %f iterations = %d \n",average , iteration);
    }

}

