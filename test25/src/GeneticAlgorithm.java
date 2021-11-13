import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {


    private double mutationRate = 0.2; // The chance for a gene (weight) to mutate
    private double weightMin = 0; // The min weight value
    private double weightMax = 1; // The max weight value
    private int population = 20 ; // The amount of "fighter - bots" (The amount of chromosomes)
    private ArrayList<Integer> matingPool; // Weights will have sex here
    private int generation = 1; // Which generation is it. Generation is a set of chromosomes;
    private int genes = 4; // Number of genes (weights)

    private double[][] chromosomes = new double [population][genes]; // Our chromosomes
    private double[] scores = new double [population];
///////////////// CONSTRUCTOR //////////////////////
    public GeneticAlgorithm () {
      // Creating our chromosomes with a random genes (random weight value)
      Random random = new Random();
      for (int i = 0; i < population; i++) {
        for(int j = 0; j < genes; j++) {
        chromosomes[i][j]=random.nextDouble(); // Some values from 0 (incl.) to 1 (excl.)
     }}
    }
///////////////////////////////////////////////////     
     
     public void createGeneration () {
// THIS METHOD WILL BE USED TO FIND THE WINNER BOTS AND MAKE BABIES TO MAKE A NEW GENERATION
// 1) Sort scores
// 2) Delete 50% of population > Pair up 1st with 2nd, 2nd with 3rd > Make them duel
// 3) Add winners of the duels to the winner arraylist
// 4) Pair up winners > Generate 4 childs (Offsprings) by 50/50 of parents chromosomes > Mutate > Add to the new generation arraylist
// 5) Start from step one with new generation
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
    



