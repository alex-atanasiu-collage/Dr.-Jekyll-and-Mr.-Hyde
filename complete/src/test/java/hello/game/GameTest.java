import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import hello.game.*;

public class GameTest {

    String name;
    Player player;
    Game game;
    Random random;

    @Before
    public void initialize() {
        game = new Game();
        random = new Random();

        name = "Name1";
        player = new Player(name);
        game.playerList.add(player);
        name = "Name2";
        player = new Player(name);
        game.playerList.add(player);
    }

    @Test
    public void constructorTest() {
        assertNotNull(game.board);
        assertNotNull(game.playerList);
    }

    @Test
    public void getPlayerTest() {
        assertEquals(game.getPlayer(name).getName(), player.getName());
    }
}