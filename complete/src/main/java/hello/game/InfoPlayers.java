package hello.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.util.Pair;

/**
 * Created by Lavini on 11/15/2016.
 */
@Component
public class InfoPlayers {

    private int hydeIndex;

    private List<Player> playerList;

    public InfoPlayers() {
        hydeIndex = 0;
        playerList = new ArrayList<>();
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public int getHydeIndex() {
        return hydeIndex;
    }

    public void setHydeIndex(int hydeIndex) {
        playerList.get(this.hydeIndex).unsetHyde();
        this.hydeIndex = hydeIndex;
        playerList.get(this.hydeIndex).setHyde();
    }

    public void addPlayer(String name, int board[][]){
        boolean existingPlayer = false;
        for (Player player: playerList){
            if (player.getName().equals(name)){
                existingPlayer = true;
            }
        }
        if (!existingPlayer){
            Player newPlayer = new Player(name);
            newPlayer.setRandomPosition(board);
            playerList.add(newPlayer);
        }
    }

    public void removePlayer(String name){
        for (int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getName().equals(name)){
                playerList.remove(i);
            }
        }
    }

    public Player getPlayer(String name){
        for(Player player : playerList){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

}
