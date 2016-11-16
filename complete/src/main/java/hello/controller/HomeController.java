package hello.controller;

import hello.game.InfoPlayers;
import hello.message.Message;
import hello.game.Game;
import hello.game.Player;
import hello.message.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    @Autowired
    private Game game;

    @Autowired
    private SimpMessagingTemplate template;

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
    public void move(Move move) throws Exception {
        Player player = game.getPlayer(move.getPlayerName());
        player.setDirection(move.getMove());
    }

    @Scheduled(fixedDelay = 10)
    public void publishUpdates(){
        if(game.getCurrentState().equals(Game.State.GAME_IN_PROGRESS));
            template.convertAndSend("/topic/movement", game.getInfoPlayers());
    }
}
