package hello.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lavini on 11/4/2016.
 */

@Component
public class Game {
    public enum State {
        APP_INITIALISED,
        GAME_IN_PROGRESS,
        GAME_ENDED
    }
    private static int HYDE_TIME = 10000;
    private static int GAME_TIME = 60000;
    private static int FRAME_TIME = 10;

    public int board[][];
    public List<Player> playerList;

    private DrJ drJ = new DrJ();
    private State currentState;
    private Timer mrHydeTimer;
    private Timer gameTimer;
    private Timer refreshTimer;

    public void setCurrentState(State state) {
        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

    public Game(){
        board = drJ.dreaptaJos;
        playerList = new ArrayList<>();
        currentState = State.APP_INITIALISED;
    }

    public Player getPlayer(String name){
        for(Player player : playerList){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

    private void changeMrHyde() {
        //TODO Logic to change Mr Hyde
        System.out.println("Mr Hyde changed!");
    }

    public void startGame() {
        currentState = State.GAME_IN_PROGRESS;
        mrHydeTimer = new Timer();
        mrHydeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                changeMrHyde();
            }
        }, 0, HYDE_TIME);

        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                endGame();
            }
        }, GAME_TIME);

        refreshTimer = new Timer();
        refreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                computePositions();
            }
        }, 0, FRAME_TIME);
    }

    private void computePositions() {
        //TODO Function to compute the next position for each player

    }

    private void endGame() {
        currentState = State.GAME_ENDED;
        gameTimer.cancel();
        mrHydeTimer.cancel();
        refreshTimer.cancel();
    }
}
