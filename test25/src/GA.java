import java.util.ArrayList;

public class GA {
    public static void heightFitness(){
        Game.hScore = Game.HEIGHT;
        outer:
        for(int i = 0 ; i < Game.HEIGHT ; i++){
            for (int j = 0; j < Game.WIDTH; j++) {
                if (Game.field[i][j] != -1) {
                    Game.hScore = i;
                    break outer;
                }
            }
        }
        //System.out.println("Height score = " + hScore);
    }
    public static void bumpFitness(){
        Game.bScore = 0;

        ArrayList<Integer> heightPerColomb = new ArrayList<Integer>(Game.WIDTH);
        for(int i = 0 ; i < Game.WIDTH ; i++){
            boolean emptyColomb = true;
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (Game.field[j][i] != -1) {
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
            Game.bScore -= Math.abs(heightPerColomb.get(i-1) - heightPerColomb.get(i));

        }
        //System.out.println("Bumpiness score = " + bScore);
    }
    public static void fullFitness(){
        Game.fScore = 0;
        for(int i = 0 ; i < Game.WIDTH ; i++){
            boolean firstPiece = false;
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (Game.field[j][i] != -1) {
                    firstPiece = true;
                }
                if (firstPiece && Game.field[j][i] == -1){
                    Game.fScore--;
                }
            }
        }
        //System.out.println("Hole score = " + fScore);
    }
    public static void totalFitness(){
        Game.tScore = Game.score + Game.hScore + Game.bScore + Game.fScore;
        System.out.println("Total score = " + Game.tScore);
    }
}
