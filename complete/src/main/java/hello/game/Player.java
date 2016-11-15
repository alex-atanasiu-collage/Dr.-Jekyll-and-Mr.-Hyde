package hello.game;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Lavini on 11/4/2016.
 */
public class Player {

    @Autowired
    private Game game;

    private int positionOx;
    private int positionOy;

    private int score;

    private String name;

    public Player(String name) {
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

    public void checkCollisionUp(){
        /*TODO
            the matrix is in game.getBoard()
            update position
        */

    }

    public void checkCollisionDown(){
        /*TODO
            the matrix is in game.getBoard()
            update position
        */

    }

    public void checkCollisionRight(){
        /*TODO
            the matrix is in game.getBoard()
            update position
        */

    }

    public void checkCollisionLeft(){
        /*TODO
            the matrix is in game.getBoard()
            update position
        */

    }

    public void move(String direction){
        /*TODO
            check collision with walls and with the other players from game.playerList
            also...updateScore() if the player collides with the crazy player
         */
        if(direction.equals("up")){
            checkCollisionUp();
            positionOy--;
            return;
        }
        if(direction.equals("right")){
            checkCollisionRight();
            positionOx++;
            return;
        }
        if(direction.equals("down")){
            checkCollisionDown();
            positionOy++;
            return;
        }
        if(direction.equals("left")){
            checkCollisionLeft();
            positionOx--;
            return;
        }
    }


}
