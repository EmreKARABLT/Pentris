import java.util.*;

public class GeneticAlgorithm {

     // Weight variables
     public static double mutationRate = 0.1; // The chance for a gene (weight) to mutate
     private static double weightMin = 0; // The min weight value
     private static double weightMax = 1; // The max weight value
     private static int genes = 7; // Number of genes (weights)

     // GA variables
     public static int population = 1000 ; // The amount of "fighter - bots" (The amount of chromosomes)
     public static int generation = 1; // Which generation is it. Generation is a set of chromosomes;
     public static double[][] chromosomes = new double [population][genes]; // Our chromosomes
     private static double[] scores = new double [population]; // Scores that bots get will be stored here
     public static Random random = new Random(); // This is used to generate random values
     Bot bot  ;
     ///////////////// CONSTRUCTOR //////////////////////
     public GeneticAlgorithm () {

          // Creating our chromosomes with a random genes (random weight value)
          for (int i = 0; i < population; i++) {
               for (int j = 0; j < genes; j++) {
                    chromosomes[i][j]=random.nextDouble(); // Some values from 0 (incl.) to 1 (excl.)
               }
          }
//          printMDArray(chromosomes);
     }
     ///////////////////////////////////////////////////

     public static double[][] createGeneration () {
          // THIS METHOD WILL BE USED TO FIND THE WINNER BOTS AND MAKE BABIES TO MAKE A NEW GENERATION
          ArrayList<double[]> matingPool = new ArrayList<double[]>(); // This is where winner bots will have sex
          double[][] newGen = new double[population][genes]; // This is where we will store our babies

          double [] mother; // These will be used to generate new babies, new offsprings (Same as Winner and Winner + 1)
          double [] father;
          // Products of mother and father
          int counterChild = 0 ;
          // 1) Delete 50% of population (Tournament selection we know from CCN)> Pair up 0th with 1st, 2nd with 3rd > Make them duel
          for (int i = 0; i < population; i = i+2){
               // 2) Add winners of the duels to the winner arraylist
               if (scores[i]>scores[i+1])
                    matingPool.add(chromosomes [i]);
               else
                    matingPool.add(chromosomes [i+1]);
          }

//          System.out.println( "Mating pool  " + matingPool.size() +" " +(Arrays.deepToString(matingPool.toArray())));
          // 3) Pair up winners
          for(int i = 0; i < matingPool.size(); i = i+2){
               mother = matingPool.get(i);
               father = matingPool.get(i+1);
               // 3.1) Generate 4 childs (Offsprings) by 50/50 of parents chromosomes
               for (int j = 0; j < 4; j++) {
                    double [] child = new double [genes];
                    for (int z = 0; z < genes; z++) {
                         // We generate a random double from 0.00 to 1.00, if it's above 0.5 we take gene from mother, if its below we take gene from the father
                         if (random.nextDouble() > 0.50) {
                              child [z] = mother[z];
                         }else {
                              child [z] = father [z]; }
                         // 3.2) Mutate if it rolls below the mutationRate
                         if (random.nextDouble()<=mutationRate){
                              child [z] = random.nextDouble();
                         }
                    }
//                    System.out.println("CHILD " + counterChild + " " +Arrays.toString(child));
                    newGen[counterChild]=child;
                    counterChild++;
                    // 3.3) Add the child to the new generation arraylist
               }
          }
//          System.out.println( "NEW GENERATION  " +(Arrays.deepToString(newGen)));

          // 4) Copy newGen to be current (new) generation (chromosomes[][]) and start from step one with new generation

          chromosomes = shuffleArray(newGen);

          // System.out.println( "NEW GENERATION  " +(Arrays.deepToString(chromosomes)));
//          getScores(chromosomes);

          //System.out.println(Arrays.toString(scores));

          //////// STATISTICS /////////////
          double average = 0;
          double max = 0;
          double min = 999;

          for (int i = 0; i<scores.length;i++){
               if(min>scores[i]) min = scores [i];
               if(max<scores[i]) max = scores[i];
               average+=scores[i];
          }
          average = average/scores.length;

          System.out.printf("==========================GENERATION %d==========================\n" , generation);
          System.out.println(Arrays.toString(scores));
          System.out.println("Average score is " + average);
          System.out.println("Max score is " + max);
          System.out.println("Min score is " + min);
          ////////////////////////////////
          generation++;
          return newGen;
     }
     public int getGeneration(){
          return this.generation;
     }

     public static double[][] getScores(double[][] chromosomes) {
// THIS METHOD WILL BE USED TO SEND CHROMOSOME INFORMATION
          for(int i=0; i<scores.length; i++){
               scores[i]=0;
          }

          for (int i = 0; i < population; i++) {
               Fitness.lineCleared_weight = chromosomes [i][0];
               Fitness.height_weight = chromosomes [i][1];
               Fitness.bumpiness_weight = chromosomes [i][2];
               Fitness.holes_weight = chromosomes [i][3];
               Fitness.sides_weight = chromosomes [i][4];
               Fitness.bottom_weight = chromosomes [i][5];
               Fitness.block_weight = chromosomes [i][6];
//               System.out.println("GAME " + i + "  -------- " + Arrays.toString(chromosomes[i]));
               Tester.looper(1, true, false, false, false);
               //          try{
//               Thread.sleep(1000);
//          }catch (Exception e ){
//          }
//          System.out.println(Bot.score);
               scores[i] = Bot.score ;
          }
//          System.out.println(Arrays.toString(scores));
          return chromosomes;
          // NEEDS CONNECTION HERE, DUNNO HOW TO FEED THOSE TO THE GAME ENGINE
     }
     public static void printMDArray(int[][] arr){
          for (int i = 0; i < arr.length; i++) {
               for (int j = 0; j < arr[0].length; j++) {
                    System.out.print(arr[i][j] + " ");
               }
               System.out.println();
          }
     }
     public static void printMDArray(double[][] arr){
          for (int i = 0; i < arr.length; i++) {
               for (int j = 0; j < arr[0].length; j++) {
                    System.out.printf("%.3f  " , arr[i][j]);
               }
               System.out.println();
          }
     }
     public static double[][] shuffleArray(double[][] arr){
          List<double[]> l = Arrays.asList(arr);
          Collections.shuffle(l);
          return l.toArray(new double[0][0]);
     }

     public static void main(String[] args) {
          GeneticAlgorithm ga = new GeneticAlgorithm();
          while(generation<100){
               getScores(chromosomes);
               createGeneration();
          }

     }
}