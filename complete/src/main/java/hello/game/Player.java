package hello.game;

import java.util.Random;

/**
 * Created by Lavini on 11/4/2016.
 */
public class Player {
    public static int SIZE = 4;
    public static int HALF_SIZE = 2;
    public static int DIMENSION_SCALE = 1000;
    public static int JEKYLL_SPEED = 200;
    public static int HYDE_SPEED = 300;

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        NONE
    }

    private int absoluteX;
    private int absoluteY;

    private Direction direction;
    private boolean isHyde;

    private int score;
    private String name;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.direction = Direction.NONE;
        this.isHyde = false;
    }

    public void setRandomPosition(int board[][]) {
        Random rand = new Random();

        int x, y;
        do {
            x = rand.nextInt(board.length) * DIMENSION_SCALE;
            y = rand.nextInt(board.length) * DIMENSION_SCALE;
        } while (wallCollision(x, y, board));
        absoluteX = x;
        absoluteY = y;
    }

    public int getAbsoluteX() {
        return absoluteX;
    }

    public int getAbsoluteY() {
        return absoluteY;
    }

    public void setRelativeX(int relativeX) {
        absoluteX = relativeX * DIMENSION_SCALE;
    }

    public int getRelativeX() {
        return absoluteX / DIMENSION_SCALE;
    }

    public void setRelativeY(int relativeY) {
        absoluteY = relativeY * DIMENSION_SCALE;
    }

    public int getRelativeY() {
        return absoluteY / DIMENSION_SCALE;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setDirection(String direction) {
        if (direction.equals("up")) {
            this.direction = Direction.UP;
        }
        if (direction.equals("right")) {
            this.direction = Direction.RIGHT;
        }
        if (direction.equals("down")) {
            this.direction = Direction.DOWN;
        }
        if (direction.equals("left")) {
            this.direction = Direction.LEFT;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setHyde() {
        isHyde = true;
    }

    public void unsetHyde() {
        isHyde = false;
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

    private boolean wallCollision(int nextX, int nextY, int board[][]) {
        int relativeX = nextX / DIMENSION_SCALE;
        int relativeY = nextY / DIMENSION_SCALE;
        // check each corner
        if (board[relativeX - HALF_SIZE][relativeY - HALF_SIZE] == 1) {
            return true;
        }
        if (board[relativeX + HALF_SIZE][relativeY - HALF_SIZE] == 1) {
            return true;
        }
        if (board[relativeX + HALF_SIZE][relativeY + HALF_SIZE] == 1) {
            return true;
        }
        if (board[relativeX - HALF_SIZE][relativeY + HALF_SIZE] == 1) {
            return true;
        }

        // check for each side
        if (board[relativeX - HALF_SIZE][relativeY] == 1) {
            return true;
        }
        if (board[relativeX + HALF_SIZE][relativeY] == 1) {
            return true;
        }
        if (board[relativeX][relativeY - HALF_SIZE] == 1) {
            return true;
        }
        if (board[relativeX][relativeY + HALF_SIZE] == 1) {
            return true;
        }
        return false;
    }

    public void advance(int board[][]) {
        int delta = isHyde? HYDE_SPEED : JEKYLL_SPEED;
        int nextX = absoluteX;
        int nextY = absoluteY;
        switch (direction) {
            case LEFT: {
                nextX -= delta;
                break;
            }
            case RIGHT: {
                nextX += delta;
                break;
            }
            case UP: {
                nextY -= delta;
                break;
            }
            case DOWN: {
                nextY += delta;
                break;
            }
        }
        if (!wallCollision(nextX, nextY, board)) {
            absoluteX = nextX;
            absoluteY = nextY;
        }
    }

}
