package hello.game;

import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Lavini on 11/4/2016.
 */

@Component
public class Game {
    public class Conf {
        public int jekyllTime;
        public int maxRounds;
    }
    public String configFile = "config.txt";
    public String scoreFile = "score.txt";
    public int board[][];
    public List<Player> playerList;

    public Game(){
        board = new int[55][55];
        playerList = new ArrayList<>();
    }

    public Player getPlayer(String name){
        for(Player player : playerList){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
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
}
