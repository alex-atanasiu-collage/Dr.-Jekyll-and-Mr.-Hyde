package hello.game;

/**
 * Created by Lavini on 11/4/2016.
 */
public class Player {

    private int positionOx;
    private int positionOy;

    private int score;
    private String name;

    public Player(String name, int board[][]) {
        this.name = name;
        this.score = 0;
        //TODO random generate position so that we are inside the labyrinth
        this.positionOx = 10;
        this.positionOy = 10;
    }

    public int getPositionOx() {
        return positionOx;
    }

    public void setPositionOx(int positionOx) {
        this.positionOx = positionOx;
    }

    public int getPositionOy() {
        return positionOy;
    }

    public void setPositionOy(int positionOy) {
        this.positionOy = positionOy;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
