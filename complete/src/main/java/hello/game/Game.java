package hello.game;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Lavini on 11/4/2016.
 */

@Component
public class Game {

    @Autowired
    private InfoPlayers infoPlayers;
    public int board[][];

    public enum State {
        APP_INITIALISED,
        GAME_IN_PROGRESS,
        GAME_ENDED
    }
    private static int HYDE_TIME = 10000;
    private static int GAME_TIME = 60000;
    private static int FRAME_TIME = 10;

    private State currentState;
    private Timer mrHydeTimer;
    private Timer gameTimer;
    private Timer refreshTimer;

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public class Conf {
        public int jekyllTime;
        public int maxRounds;
    }

    public String configFile = "config.txt";
    public String scoreFile = "score.txt";

    public InfoPlayers getInfoPlayers() {
        return infoPlayers;
    }

    public void setInfoPlayers(InfoPlayers infoPlayers) {
        this.infoPlayers = infoPlayers;
    }

    public Game(){
        currentState = State.APP_INITIALISED;
        //generate random maze
        Random rand = new Random();
        DrJ one = new DrJ(rand.nextInt(3) + 1);
        DrS two = new DrS(rand.nextInt(3) + 1);
        StJ three = new StJ(rand.nextInt(3) + 1);
        StS four = new StS(rand.nextInt(3) + 1);
        int [][] sts = four.get();
        int [][] stj = three.get();
        int [][] drj = one.get();
        int [][] drs = two.get();
        System.out.println(sts.length + " " +  stj.length + drs[0].length + drj[0].length);
        board = new int[sts.length + stj.length][sts[0].length + drs[0].length];
        int i, j, k, m;
        for (i = 0; i < sts.length; i ++) {
            for (j = 0; j < sts[0].length; j++) {
                board[i][j] = sts[i][j];
            }
            k = j;
            for (j = 0; j < drs[0].length; j++) {
                board[i][k + j] = drs[i][j];
            }
        }
        m = i;
        for (i = 0; i < stj.length; i ++) {
            for (j = 0; j < stj[0].length; j++) {
                board[m + i][j] = stj[i][j];
            }
            k = j;
            for (j = 0; j < drj[0].length; j++) {
                board[m + i][k + j] = drj[i][j];
            }
        }
    }

    public Player getPlayer(String name){
        return infoPlayers.getPlayer(name);
    }

    public void addPlayer(String name){
        infoPlayers.addPlayer(name, board);
    }

    public void removePlayer(String name){
        infoPlayers.removePlayer(name);
    }

    public ArrayList<Pair<String, Integer>> getScore (String fileName) {
        ArrayList<Pair<String, Integer>> scores = new ArrayList<>();
        String[] info;
        String nextLine, name;
        Integer score;
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(fileName));

            while ((nextLine = inFile.readLine()) != null) {
                info = nextLine.split(" ");
                name = info[0];
                score = Integer.parseInt(info[1]);
                scores.add(new Pair<>(name, score));
            }
            inFile.close();
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public void setScore (String fileName,String winner) {
        ArrayList<Pair<String, Integer>> scores = new ArrayList<>();
        String[] info;
        String nextLine, name;
        Integer score;
        boolean found = false;
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(fileName));

            while ((nextLine = inFile.readLine()) != null) {
                info = nextLine.split(" ");
                name = info[0];
                score = Integer.parseInt(info[1]);
                if (name.compareTo(winner) == 0) {
                    score += 1;
                    found = true;
                }
                scores.add(new Pair<>(name, score));
            }
            if (found == false) {
                scores.add(new Pair<>(winner, 1));
            }
            inFile.close();

            Collections.sort(scores, new Comparator<Pair<String, Integer>>() {
                @Override
                public int compare(final Pair<String, Integer> o1, final Pair<String, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }
            });
            // Clean file
            PrintWriter writer = new PrintWriter(scoreFile);
            writer.print("");

            for (Pair<String,Integer> player: scores)
                writer.println(player.getKey() + " " + player.getValue());
            writer.close();
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Conf getConfig(String configFile) {
        String[] info;
        String nextLine;
        Conf config = new Conf();

        try {
            BufferedReader inFile = new BufferedReader(new FileReader(configFile));

            nextLine = inFile.readLine();
            info = nextLine.split(" ");
            config.maxRounds = Integer.parseInt(info[1]);

            nextLine = inFile.readLine();
            info = nextLine.split(" ");
            config.jekyllTime =  Integer.parseInt(info[1]);
            inFile.close();
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(config.maxRounds + " "+ config.jekyllTime);
        return config;
    }

    private void changeMrHyde() {
        //TODO Logic to change Mr Hyde

        int hideIndex = -1;
        int minDistance = 10000;

        List<Player> players = infoPlayers.getPlayerList();
        Player drHyde = players.get(infoPlayers.getHydeIndex());
        for(int i = 0; i < players.size(); i++){
            if(i != infoPlayers.getHydeIndex()){
                Player player = players.get(i);
                int a = Math.abs(player.getPositionOx() - drHyde.getPositionOx());
                a = a * a;
                int b = Math.abs(player.getPositionOy() - drHyde.getPositionOy());
                b = b * b;
                if(Math.sqrt(a + b) < minDistance){
                    minDistance = (int)Math.sqrt(a + b);
                    hideIndex = i;
                }
            }
        }

        infoPlayers.setHydeIndex(hideIndex);
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
