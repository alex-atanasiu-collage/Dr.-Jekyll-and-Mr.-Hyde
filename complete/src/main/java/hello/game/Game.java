package hello.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lavini on 11/4/2016.
 */

@Component
public class Game {

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

}
