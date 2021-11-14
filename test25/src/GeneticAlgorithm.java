import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class GeneticAlgorithm {


    private double mutationRate = 0.2; // The chance for a gene (weight) to mutate
    private double weightMin = 0; // The min weight value
    private double weightMax = 1; // The max weight value
    private int population = 20 ; // The amount of "fighter - bots" (The amount of chromosomes)
    private ArrayList<Integer> matingPool; // Weights will have sex here
    private int generation = 1; // Which generation is it. Generation is a set of chromosomes;
    private int genes = 4; // Number of genes (weights)
    public Random random = new Random(); // This is used to generate random values

    private double[][] chromosomes = new double [population][genes]; // Our chromosomes
    private double[] scores = new double [population];
///////////////// CONSTRUCTOR //////////////////////
    public GeneticAlgorithm () {
      // Creating our chromosomes with a random genes (random weight value)
      for (int i = 0; i < population; i++) {
        for(int j = 0; j < genes; j++) {
        chromosomes[i][j]=random.nextDouble(); // Some values from 0 (incl.) to 1 (excl.)
     }}
    }
///////////////////////////////////////////////////     
     
     public void createGeneration () {
ArrayList<double[]> winnerBots = new ArrayList<double[]>(); // Winner bot array
ArrayList<double[]> newGen = new ArrayList<double[]>(); // This is where we will store our babies
double [] mother = new double [genes]; // These will be used to generate new babies, new offsprings (Same as Winner and Winner + 1)
double [] father = new double [genes]; 

double [] child = new double [genes]; // Products of mother and father
// THIS METHOD WILL BE USED TO FIND THE WINNER BOTS AND MAKE BABIES TO MAKE A NEW GENERATION
// 1) Sort scores
Arrays.sort(scores);
// 2) Delete 50% of population > Pair up 0th with 1st, 2nd with 3rd > Make them duel
for (int i = 0; i < population * 0.5; i=i+2){
// 3) Add winners of the duels to the winner arraylist
     if (scores[i]>scores[i+1]) { winnerBots.add(chromosomes[i]); }
     else { winnerBots.add(chromosomes[i+1]); }
}
// 4) Pair up winners
for (int i = 0; i < population * 0.25; i = i+2) {
mother = winnerBots.get(i);
father = winnerBots.get(i+1);
// 4.1) TO DO: Generate 4 childs (Offsprings) by 50/50 of parents chromosomes
//   for (int j = 0; j < 4; j++)
//        for (int z = 0; z < genes; z++)
// 4.2) Mutate
     if (random.nextDouble()<=mutationRate){
// Change the z (gene) by some amount (Q: What amount?)
     }   
// 4.3) Add to the new generation arraylist
newGen.add(child);

 }
// 5) Copy newGen to be current Generation (chromosomes[][]) and start from step one with new generation
    }
    
     public void sendWeightsToBot () {
// THIS METHOD WILL BE USED TO SEND CHROMOSOME INFORMATION
// Something like this:
// Fitness.height_weight = chromosomes [bla][blah];
// Fitness.bumpiness_weight = chromosomes [bla][blah+1]
// ...
     }
     public void printer () {
// THIS METHOD WILL BE USED TO PRINT ALL THE RELEVANT INFORMATION LIKE GENERATION, GENES (WEIGHTS)
     }
    
    }
    



