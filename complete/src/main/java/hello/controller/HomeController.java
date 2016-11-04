package hello.controller;

import hello.greeting.Greeting;
import hello.greeting.HelloMessage;
import hello.game.Game;
import hello.game.Player;
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
    public Greeting greetingHi(HelloMessage message) throws Exception {
        return new Greeting(message.getName() + " has entered the game.");
    }

    @MessageMapping("/bye")
    @SendTo("/topic/greetings")
    public Greeting greetingBye(HelloMessage message) throws Exception {
        return new Greeting(message.getName() + " has left the game.");
    }

    @MessageMapping("/start")
    @SendTo("/topic/game")
    public Game getInfo(HelloMessage message) throws Exception {
        boolean existingPlayer = false;
        for (Player player: game.playerList){
            if (player.getName().equals(message.getName())){
                existingPlayer = true;
            }
        }
        if (!existingPlayer){
            game.playerList.add(new Player(message.getName()));
        }
        System.out.println(game.playerList.size());
        return game;
    }

    @MessageMapping("/end")
    @SendTo("/topic/game")
    public Game getEnd(HelloMessage message) throws Exception {
        for (int i = 0; i < game.playerList.size(); i++){
            if(game.playerList.get(i).getName().equals(message.getName())){
                game.playerList.remove(i);
                return game;
            }
        }
        return game;
    }

}
