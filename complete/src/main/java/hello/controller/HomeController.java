package hello.controller;

import hello.message.Message;
import hello.game.Game;
import hello.game.Player;
import hello.message.Move;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    @Autowired
    Game game;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greetingHi(Message message) throws Exception {
        return new Message(message.getContent() + " has entered the game.");
    }

    @MessageMapping("/bye")
    @SendTo("/topic/greetings")
    public Message greetingBye(Message message) throws Exception {
        return new Message(message.getContent() + " has left the game.");
    }

    @MessageMapping("/start")
    @SendTo("/topic/game")
    public Game getInfo(Message message) throws Exception {
        boolean existingPlayer = false;
        for (Player player: game.playerList){
            if (player.getName().equals(message.getContent())){
                existingPlayer = true;
            }
        }
        if (!existingPlayer){
            game.playerList.add(new Player(message.getContent()));
        }
        return game;
    }

    @MessageMapping("/end")
    @SendTo("/topic/game")
    public Game getEnd(Message message) throws Exception {
        for (int i = 0; i < game.playerList.size(); i++){
            if(game.playerList.get(i).getName().equals(message.getContent())){
                game.playerList.remove(i);
                return game;
            }
        }
        return game;
    }

    @MessageMapping("/move")
    @SendTo("/topic/game")
    public Game move(Move move) throws Exception{
        Thread.sleep(1000);
        System.out.println(move.getPlayerName() + "   " + move.getMove());
        Player player = game.getPlayer(move.getPlayerName());
        //Test
        game.getConfig(game.configFile);
        player.setScore(player.getScore() + 1);
        player.move(move.getMove());
        return game;
    }

}
