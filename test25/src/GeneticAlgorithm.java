import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GeneticAlgorithm {
    public static String s = "";
    public static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("generation_weights_1point_crossover_best_1_8_0.1.csv", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Weight variables
    public static double mutationRate = 0.05; // The chance for a gene (weight) to mutate
    private static final int genes = 6; // Number of genes (weights)

    // GA variables
    public static int population = 160 ; // The amount of "fighter - bots" (The amount of chromosomes)
    public static int generation = 1; // Which generation is it. Generation is a set of chromosomes;
    public static double[][] chromosomes = new double [population][genes]; // Our chromosomes
    private static final double[] scores = new double [population]; // Scores that bots get will be stored here
    public static int min ;
    public static  StringBuilder builder;
    public static Random random = new Random(); // This is used to generate random values
    ///////////////// CONSTRUCTOR //////////////////////
    public GeneticAlgorithm () {
        builder = new StringBuilder();
        for (int i = 0; i < population; i++) {
            for (int j = 0; j < genes; j++) {
                chromosomes[i][j]=random.nextDouble(); // Some values from 0 (incl.) to 1 (excl.)
            }
        }
//          printMDArray(chromosomes);
    }
    ///////////////////////////////////////////////////
    public static void geneticAlgorithmRun() {
        // Creating our chromosomes with a random genes (random weight value)
		Game.isGAon = true ;

        new GeneticAlgorithm();
        while(generation<=30){
            getScores(chromosomes);
            createGeneration();
        }
        writer.print(builder.toString());
        writer.close();


    }
    public static void createGeneration () {
    	// THIS METHOD WILL BE USED TO FIND THE WINNER BOTS AND MAKE BABIES TO MAKE A NEW GENERATION
        ArrayList<double[]> matingPool = new ArrayList<>(); // This is where winner bots will have sex

        double[][] newGen = new double[population][genes]; // This is where we will store our babies

        double [] mother; // These will be used to generate new babies, new offsprings (Same as Winner and Winner + 1)
        double [] father;
        // Products of mother and father
        int counterChild = 0 ;
        // 1) Delete 50% of population (Tournament selection we know from CCN)> Pair up 0th with 1st, 2nd with 3rd > Make them duel
//        for (int i = 0; i < population; i = i+2){
//            // 2) Add winners of the duels to the winner arraylist
//            if (scores[i]>scores[i+1])
//                matingPool.add(chromosomes [i]);
//            else
//                matingPool.add(chromosomes [i+1]);
//        }

          matingPool = getParents(scores , chromosomes , population/8);

//          System.out.println( "Mating pool  " + matingPool.size() +" " +(Arrays.deepToString(matingPool.toArray())));
        // 3) Pair up winners
        for(int i = 0; i < matingPool.size(); i = i+2){
            mother = matingPool.get(i);
            father = matingPool.get(i+1);
            // 3.1) Generate 4 children (Offsprings) by 50/50 of parents chromosomes
            for (int j = 0; j < population/matingPool.size(); j++) {
            	int k_point = random.nextInt(genes-1);
                double [] child = new double [genes];
                double [] child1 = new double [genes];

                for (int z = 0; z < genes; z++) {
                    // We generate a random double from 0.00 to 1.00, if it's above 0.5 we take gene from mother, if its below we take gene from the father
	                if(z <= k_point){
						child[z] = mother[z];
						child1[z] = father[z];
					}else{
                	    child[z] = father[z];
                	    child1[z] = mother[z];
                    }

//                    if (rand < 0.5) {
//                        child[z] = mother[z];
//                    }else {
//                        child[z] = father[z];
//                    }
                    // 3.2) Mutate if it rolls below the mutationRate
                    if (random.nextDouble()<=mutationRate){
                        child [z] = random.nextDouble();
                    }
                    if (random.nextDouble()<=mutationRate){
                        child1[z] = random.nextDouble();
                    }
                }
//                    System.out.println("CHILD " + counterChild + " " +Arrays.toString(child));
                newGen[counterChild]=child;
                newGen[counterChild+1]=child1;
                counterChild = counterChild + 2 ;
                // 3.3) Add the child to the new generation arraylist
            }
        }
//          System.out.println( "NEW GENERATION  " +(Arrays.deepToString(newGen)));

        // 4) Copy newGen to be current (new) generation (chromosomes[][]) and start from step one with new generation
        int indexOfMax = 0 ;
        double max = 0 ;
        for(int i = 0 ; i < scores.length ; i++){
            if(scores[i] > max) {
                max = scores[i];
                indexOfMax = i;
            }
        }


        // System.out.println( "NEW GENERATION  " +(Arrays.deepToString(chromosomes));
//          getScores(chromosomes);

        //System.out.println(Arrays.toString(scores));

        //////// STATISTICS /////////////
        double average = 0;
//          double max = 0;
//          double min = 999;

        for (double score : scores) {
//               if(min>scores[i]) min = scores [i];
//               if(max<scores[i]) max = scores[i];
            average += score;
        }
        average = average/scores.length;

        System.out.printf("==========================GENERATION %d==========================\n" , generation);
        System.out.println(Arrays.toString(scores));
        System.out.println("Average score is " + average);
        System.out.println("Max score is " + Tester.maxScore);
        System.out.println("Min score is " + min);
        System.out.println(generation + "," + Tester.maxScore + "," + min + "," + average + "," + scores[indexOfMax] + "," + Arrays.toString(chromosomes[indexOfMax]) + ",\n\n");

        builder.append(generation + "," + Tester.maxScore + "," + min + "," + average + "," + scores[indexOfMax] + "," + Arrays.toString(chromosomes[indexOfMax]) + ",\n");

        chromosomes = shuffleArray(newGen);

        generation++;

        ////////////////////////////////
    }

    public static double[][] getScores(double[][] chromosomes) {
// THIS METHOD WILL BE USED TO SEND CHROMOSOME INFORMATION
        Arrays.fill(scores, 0);

        for (int i = 0; i < population; i++) {
            Fitness.lineCleared_weight = chromosomes[i][0];
            Fitness.height_weight = chromosomes[i][1];
            Fitness.bumpiness_weight = chromosomes[i][2];
            Fitness.holes_weight = chromosomes[i][3];
            Fitness.sides_weight = chromosomes[i][4];
            Fitness.bottom_weight = chromosomes[i][5];

//               System.out.println("GAME " + i + "  -------- " + Arrays.toString(chromosomes[i]));
            Tester.looper(200, true, false, false, false);

            scores[i] = Tester.average ;
        }
//          System.out.println(Arrays.toString(scores));
        return chromosomes;
        // NEEDS CONNECTION HERE, DUNNO HOW TO FEED THOSE TO THE GAME ENGINE
    }

    public static ArrayList<double[]> getParents(double[] score , double[][] chromosome , int parentNumber){
//          parentNumber = ( parentNumber  ) * 4 ;
        ArrayList<Double> score_al = new ArrayList<>();
        ArrayList<double[]> chromosome_al = new ArrayList<>();
        ArrayList<double[]> parents_al= new ArrayList<>();
        for(int i = 0  ; i < score.length ; i++){
            score_al.add(score[i]);
            chromosome_al.add(chromosome[i]);
        }
        while(parents_al.size() < parentNumber) {
            double max = 0;
            int indexOfMax = 0;
            for (int i = 0; i < score_al.size(); i++) {
                if (score_al.get(i) > max) {
                    max = score_al.get(i);
                    indexOfMax = i;
                }
            }
//               System.out.println(max );
            parents_al.add(chromosome_al.get(indexOfMax));
            score_al.remove(indexOfMax);
            chromosome_al.remove(indexOfMax);
        }
        return parents_al;
    }
    public static double[][] shuffleArray(double[][] arr){
        List<double[]> l = Arrays.asList(arr);
        Collections.shuffle(l);
        return l.toArray(new double[0][0]);
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        geneticAlgorithmRun();
    }
}