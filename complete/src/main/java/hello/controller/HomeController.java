package hello.controller;

import hello.game.InfoPlayers;
import hello.message.Message;
import hello.game.Game;
import hello.game.Player;
import hello.message.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    @Autowired
    private Game game;

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
        game.addPlayer(message.getContent());
        return game; //board + infoPlayers
    }

    @MessageMapping("/end")
    @SendTo("/topic/game")
    public InfoPlayers getEnd(Message message) throws Exception {
        game.removePlayer(message.getContent());
        return game.getInfoPlayers();
    }

    @MessageMapping("/move")
    @SendTo("/topic/movement")
    public InfoPlayers move(Move move) throws Exception{

        Player player = game.getPlayer(move.getPlayerName());
        player.setPositionOx(player.getPositionOx() + 3);

        return game.getInfoPlayers();
    }

}
