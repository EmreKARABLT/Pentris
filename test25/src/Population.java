import java.util.ArrayList;

public class Population {
    private int mutationRate;
    private int[] population;
    private ArrayList<Integer> matingPool;
    private int generation;
    public Population(int mutationRate , int pop_len){
        this.mutationRate = mutationRate;
        this.population = new int[pop_len];
        this.matingPool = new ArrayList<Integer>();
        this.generation = 0;

    }
}
