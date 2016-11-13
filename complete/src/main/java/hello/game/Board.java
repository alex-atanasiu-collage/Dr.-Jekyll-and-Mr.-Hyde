package hello.game;
import java.util.Random;
/**
 * Created by adriana on 09.11.2016.
 */
public class Board {
    int [][] myBoard;
    private DrJ drj;
    Board() {
        Random rand = new Random();
        //drj = new DrJ();
        //DrS drs= new DrS(1);
        //StJ stj= new StJ(1);
        //StS sts= new StS(1);
        /*System.out.println(sts.stangaSus.length + " " +  stj.stangaJos.length + drs.dreaptaSus[0].length + drj.dreaptaJos[0].length);
        myBoard = new int[sts.stangaSus.length + stj.stangaJos.length][sts.stangaSus[0].length + drs.dreaptaSus[0].length];
        int i, j, k, m;
        for (i = 0; i < sts.stangaSus.length; i ++) {
            for (j = 0; j < sts.stangaSus[0].length; j++) {
                myBoard[i][j] = sts.stangaSus[i][j];
            }
            k = j;
            for (j = 0; j < drs.dreaptaSus[0].length; j++) {
                myBoard[i][k + j] = drs.dreaptaSus[i][j];
            }
        }
        m = i;
        for (i = 0; i < stj.stangaJos.length; i ++) {
            for (j = 0; j < stj.stangaJos[0].length; j++) {
                myBoard[m + i][j] = stj.stangaJos[i][j];
            }
            k = j;
            for (j = 0; j < drj.dreaptaJos[0].length; j++) {
                myBoard[m + i][k + j] = drj.dreaptaJos[i][j];
            }
        }*/
    }
}
