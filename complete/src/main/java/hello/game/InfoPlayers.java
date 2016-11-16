package hello.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        this.hydeIndex = hydeIndex;
    }

    public void addPlayer(String name, int board[][]){
        boolean existingPlayer = false;
        for (Player player: playerList){
            if (player.getName().equals(name)){
                existingPlayer = true;
            }
        }
        if (!existingPlayer){
            //generate random position so that we are inside the labyrinth
            boolean ok = true;
            Random rand = new Random();
            int x, y;
            do {
                x = rand.nextInt(100);
                y = rand.nextInt(100);
                if( x < 3 || y < 3 ){
                    continue;
                }
                ok = false;
                for(int i = 0; i <= 3; i++) {
                    if (board[x][y + i] == 1 || board[x][y - i] == 1 || board[x + i][y] == 1 || board[x - i][y] == 1) {
                        ok = true;
                    }
                }
            } while (ok);
            playerList.add(new Player(name, board, x, y));
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
