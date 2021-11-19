import java.util.ArrayList;

public class Fitness{
    private static double score ;
    static double heightScore = 0;
    static double bumpinessScore = 0;
    static double holeScore = 0;
    static double fitnessValue = 0;
    static double touchingSides = 0;
    static double touchingBottom = 0;

    static double lineCleared_weight = 2;
    static double height_weight = 1;
    static double bumpiness_weight = 1.5;
    static double holes_weight = 1.8;
    static double sides_weight = 1;
    static double bottom_weight = 1;
    static double block_weight = 0;


    //
//    public Fitness(){
//        this.score = 0 ;
//        this.height_weight = 0.78075;
//        this.bumpiness_weight = 0.3925;
//        this.holes_weight = 0.82075;
//        this.lineCleared_weight = 0.55;
//
//    }
    public static double heightFitness(int[][] grid){
        double heightScore = Game.HEIGHT;
        outer:
        for(int i = 0 ; i < Game.HEIGHT ; i++){
            for (int j = 0; j < Game.WIDTH; j++) {
                if (grid[i][j] != -1) {
                    heightScore = i;
                    break outer;
                }
            }
        }
//        System.out.println("Height score = " + heightScore);
        return heightScore;
    }
    public static double bumpFitness(int[][] grid){
        double bumpinessScore = 0;

        ArrayList<Integer> heightPerColomb = new ArrayList<Integer>(Game.WIDTH);
        for(int i = 0 ; i < Game.WIDTH ; i++){
            boolean emptyColomb = true;
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (grid[j][i] != -1) {
                    heightPerColomb.add(j);
                    emptyColomb = false;
                    break;
                }
            }
            if (emptyColomb == true) {
                heightPerColomb.add(Game.HEIGHT);
            }
        }

        for (int i = 1; i < heightPerColomb.size(); i++){
            bumpinessScore -= Math.abs(heightPerColomb.get(i-1) - heightPerColomb.get(i));

        }
//        System.out.println("Bumpiness score = " + bumpinessScore);
        return bumpinessScore;
    }
    public static double holes(int[][] grid){
        double holeScore = 0;
        for(int i = 0 ; i < Game.WIDTH ; i++){
            boolean firstPiece = false;
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (grid[j][i] != -1) {
                    firstPiece = true;
                }
                if (firstPiece && grid[j][i] == -1){
                    holeScore--;
                }
            }
        }
//        System.out.println("Hole score = " + holeScore);
        return holeScore;
    }

    public static double calculateFitness(int[][] grid ){

        GeneticAlgorithm ga = new GeneticAlgorithm();
        double [][] weights = ga.sendWeightsToBot(ga.chromosomes);
        int ga_num = ga.getGeneration();
        double fitnessValue = Game.scoreForMove * weights[ga_num][0] +
                        (heightFitness(grid) * weights[ga_num][1]) +
                        (bumpFitness(grid) * weights[ga_num][2]) +
                        (holes(grid) * weights[ga_num][3]) +
                        (touchingSides(grid) * weights[ga_num][4]) +
                        (touchingBottom(grid) * weights[ga_num][5] ) +
                        (blocking(grid) * weights[ga_num][6]);
        return fitnessValue;
    }
    
    public static double touchingSides(int[][] grid){
        touchingSides = 0;
        for (int i = 0; i < Game.HEIGHT; i++){
            if (grid[i][0] != -1) {
                touchingSides++;    
            }    
        }
        for (int i = 0; i < Game.HEIGHT; i++){
            if (grid[i][Game.WIDTH-1] != -1) {
                touchingSides++;
            }  
        }
        return touchingSides;
    }
    

    public static double touchingBottom(int[][] grid){
        touchingBottom = 0;
        for (int i = 0; i < Game.WIDTH; i++){
            if (grid[Game.HEIGHT-1][i] != -1) {
                touchingBottom++;
            }  
        }
        return touchingBottom;
    }
    
    public static double blocking(int[][] grid){
        double BlockScore = 0;
        int holeHeight = 0;
        for(int i = 0 ; i < Game.WIDTH ; i++){
            int topPiece = 15;
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (grid[j][i] != -1) {
                    topPiece = j;
                    break;
                }
            }    
            for (int j = 14; j > topPiece; j--) {
                if (grid[j][i] == -1 ){
                    holeHeight = j;
                    break;
                }
            }
            for (int j = 0; j < holeHeight; j++) {
                if (grid[j][i] != -1) {
                    BlockScore--;
                }
            }
        }
        return BlockScore;
    }
    

    public static double calculateOtherFitness(int[][] grid ){

        double fitnessValue = 
        (double)Game.scoreForMove * lineCleared_weight +
        (heightFitness(grid) * height_weight) +
        (bumpFitness(grid) * bumpiness_weight) +
        (holes(grid) * holes_weight) +
        (touchingSides(grid) * sides_weight) +
        (touchingBottom(grid) * bottom_weight) + 
        (blocking(grid) * block_weight);
        return fitnessValue;
    }
    
}
