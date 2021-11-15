import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

     // Weight variables
     private double mutationRate = 0.1; // The chance for a gene (weight) to mutate
     private double weightMin = 0; // The min weight value
     private double weightMax = 1; // The max weight value
     private int genes = 4; // Number of genes (weights)

     // GA variables
     private int population = 20 ; // The amount of "fighter - bots" (The amount of chromosomes)
     private int generation = 1; // Which generation is it. Generation is a set of chromosomes;
     private double[][] chromosomes = new double [population][genes]; // Our chromosomes
     private double[] scores = new double [population]; // Scores that bots get will be stored here
     public Random random = new Random(); // This is used to generate random values

     ///////////////// CONSTRUCTOR //////////////////////
     public GeneticAlgorithm () {
     // Creating our chromosomes with a random genes (random weight value)
      for (int i = 0; i < population; i++) {
        for (int j = 0; j < genes; j++) {
        chromosomes[i][j]=random.nextDouble(); // Some values from 0 (incl.) to 1 (excl.)
     }}}
     ///////////////////////////////////////////////////     

public void createGeneration () {
// THIS METHOD WILL BE USED TO FIND THE WINNER BOTS AND MAKE BABIES TO MAKE A NEW GENERATION
     ArrayList<double[]> matingPool = new ArrayList<double[]>(); // This is where winner bots will have sex
     ArrayList<double[]> newGen = new ArrayList<double[]>(); // This is where we will store our babies

     double [] mother = new double [genes]; // These will be used to generate new babies, new offsprings (Same as Winner and Winner + 1)
     double [] father = new double [genes]; 
     
     double [] child = new double [genes]; // Products of mother and father

// 1) Delete 50% of population (Tournament selection we know from CCN)> Pair up 0th with 1st, 2nd with 3rd > Make them duel
     for (int i = 0; i < population; i = i+2){
// 2) Add winners of the duels to the winner arraylist
     if (scores[i]>scores[i+1]) { matingPool.add(chromosomes [i]); }
                           else { matingPool.add(chromosomes [i+1]); } }
// 3) Pair up winners
     for (int i = 0; i < population * 0.5; i = i+2) {
          mother = matingPool.get(i);
          father = matingPool.get(i+1);
// 3.1) Generate 4 childs (Offsprings) by 50/50 of parents chromosomes
     for (int j = 0; j < 4; j++) {
       for (int z = 0; z < genes; z++) {
// We generate a random double from 0.00 to 1.00, if it's above 0.5 we take gene from mother, if its below we take gene from the father
     if (random.nextDouble() > 0.50) { child [z] = mother [z]; }
                                else { child [z] = father [z]; }
// 3.2) Mutate if it rolls below the mutationRate
     if (random.nextDouble()<=mutationRate){
               child [z] = random.nextDouble(); }   
}
// 3.3) Add the child to the new generation arraylist
     newGen.add(child);
}
}
// 4) Copy newGen to be current (new) generation (chromosomes[][]) and start from step one with new generation
     chromosomes = newGen.toArray(new double [0][0]);
     sendWeightsToBot(chromosomes);
     generation++; // Next generation
}

public void sendWeightsToBot (double[][] chromosomes) {
// THIS METHOD WILL BE USED TO SEND CHROMOSOME INFORMATION
     for (int i = 0; i < population; i++) {
          Fitness.lineCleared_weight = chromosomes [i][0];
          Fitness.height_weight = chromosomes [i][1];
          Fitness.bumpiness_weight = chromosomes [i][2];
          Fitness.holes_weight = chromosomes [i][3];
     }
     // NEEDS CONNECTION HERE, DUNNO HOW TO FEED THOSE TO THE GAME ENGINE
}

}
    



