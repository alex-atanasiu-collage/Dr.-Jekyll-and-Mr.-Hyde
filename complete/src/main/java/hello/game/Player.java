package hello.game;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Lavini on 11/4/2016.
 */
public class Player {

    @Autowired
    private Game game= new Game();
    public int matrix[][];
    private int positionOx;
    private int positionOy;
    public int whosHyde = 0;
    private int score;

    private String name;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        //TODO random generate position so that we are inside the labyrinth
        this.positionOx = 10;
        this.positionOy = 10;
    }
    public boolean isHyde(){
        return false;
    }

    public int getPositionOx()
    {
        return positionOx;
    }

    public int getPositionOxHyde() {
        if (isHyde()== true)
            return positionOx;
        else
            return -1;


    }

    public int getPositionOyHyde() {
        if (isHyde()==true)
            return positionOy;
        else
            return -1;


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

    public void updatePosition(int positionOx, int positionOy) {
        this.positionOx = positionOx;
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


    public void checkCollisionUp() {

        matrix = game.getBoard();
         if((getPositionOx()-3)>=0 && (getPositionOy()-4)>=0)
            if (matrix[getPositionOx()-3][getPositionOy() - 4 ] == 1 || matrix[getPositionOx()-2][getPositionOy() - 4 ] == 1 || matrix[getPositionOx()-1][getPositionOy() - 4 ] == 1 ||
                matrix[getPositionOx()][getPositionOy() - 4 ] == 1 || matrix[getPositionOx()+1][getPositionOy() - 4 ] == 1 || matrix[getPositionOx()+2][getPositionOy() - 4 ] == 1) {
            int x4=getPositionOx();
            int y4=getPositionOy();
            updatePosition(x4, y4-1);
            System.out.println("collision");
            System.out.println(x4);
            System.out.println(y4-1);
        } else{
            updatePosition(getPositionOx(),getPositionOy() );
            System.out.println(getPositionOx());
            System.out.println(getPositionOy());
        }
//        if (!isHyde() && getPositionOyHyde() == positionOy - 1) {
//            TODO
            //randompos();
//
//        }
    }


    private void checkCollisionDown() {

        matrix = game.getBoard();
        if((getPositionOx()-3)>=0 )
            if (matrix[getPositionOx()-3][getPositionOy() + 4 ] == 1 || matrix[getPositionOx()-2][getPositionOy() + 4 ] == 1 || matrix[getPositionOx()-1][getPositionOy() + 4 ] == 1 ||
                matrix[getPositionOx()][getPositionOy() + 4 ] == 1 || matrix[getPositionOx()+1][getPositionOy() + 4 ] == 1 || matrix[getPositionOx()+2][getPositionOy() + 4 ] == 1){
                int y1=getPositionOy() ;
                int x1=getPositionOx();
                updatePosition(x1,y1-1);
                System.out.println("Fuck collision");
                System.out.println(x1);
                System.out.println(y1+1);
        }
        else{
            updatePosition(getPositionOx(), getPositionOy());
            System.out.println(getPositionOx());
            System.out.println(getPositionOy()+1);}
        if (!isHyde() && getPositionOyHyde() == positionOy+1 ) {
//          TODO randompos
        }


    }

    private void checkCollisionRight() {
        matrix = game.getBoard();
    if((getPositionOy()-3)>=0)
            if (matrix[getPositionOx()+4][getPositionOy()- 3] == 1 || matrix[getPositionOx()+4][getPositionOy()- 2] == 1 || matrix[getPositionOx()+4][getPositionOy()- 1] == 1 ||
            matrix[getPositionOx()+4][getPositionOy()] == 1 ||matrix[getPositionOx()+4][getPositionOy() + 1] == 1 ||matrix[getPositionOx()+4][getPositionOy()+ 2] == 1 ||matrix[getPositionOx()+4][getPositionOy() + 3] == 1 ){
            int x2=getPositionOx();
            int y2=getPositionOy();
            updatePosition(x2-1, y2);
            System.out.println("Collision 2");
            System.out.println(x2+1);
            System.out.println(getPositionOy());
        }
        else{
            updatePosition(getPositionOx() , getPositionOy());
            System.out.println(getPositionOx()+1);
            System.out.println(getPositionOy());}
//        if (!isHyde() && getPositionOxHyde() == positionOx + 1) {
//              TODO randompos
//        }

    }

    public void checkCollisionLeft() {

        matrix = game.getBoard();
        if((getPositionOy()-3)>=0 && (getPositionOx()-4)>=0)
            if (matrix[getPositionOx()-4][getPositionOy()- 3] == 1 || matrix[getPositionOx()-4][getPositionOy()- 2] == 1 || matrix[getPositionOx()-4][getPositionOy()- 1] == 1 ||
            matrix[getPositionOx()-4][getPositionOy()] == 1 ||matrix[getPositionOx()-4][getPositionOy() + 1] == 1 ||matrix[getPositionOx()-4][getPositionOy()+ 2] == 1 ||matrix[getPositionOx()-4][getPositionOy() + 3] == 1 ){
            int x3=getPositionOx();
            int y3=getPositionOy();
            updatePosition(x3+1, y3);
            System.out.println("Coooooooooooooliiiiiiiiiideeeeee");
            System.out.println(x3-1);
            System.out.println(y3);
            }
        else{
            updatePosition(getPositionOx() , getPositionOy());
            System.out.println(getPositionOx()- 1);
            System.out.println(getPositionOy());}
//        if (!isHyde() && getPositionOxHyde() == positionOx - 1) {
//          TODO randompos
//        }


    }

    public void move(String direction) {

        if (direction.equals("up")) {
            checkCollisionUp();
            positionOy--;
            return;
        }
        if (direction.equals("right")) {
            checkCollisionRight();
            positionOx++;
            return;
        }
        if (direction.equals("down")) {
            checkCollisionDown();
            positionOy++;
            return;
        }
        if (direction.equals("left")) {
            checkCollisionLeft();
            positionOx--;
            return;
        }
    }


}
