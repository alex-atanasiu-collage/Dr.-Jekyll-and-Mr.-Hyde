import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import hello.game.*;

public class PlayerTest {

    String name;
    Player player;
    Random random;

    @Before
    public void initialize() {
        name = "Name";
        player = new Player(name);
        random = new Random();
    }

    @Test
    public void constructorTest() {
        assertEquals(player.getName(), name);
        assertEquals(player.getScore(), 0);
    }

    @Test
    public void positionTest() {
        int oxPos = random.nextInt();
        int oyPos = random.nextInt();
        player.setPositionOx(oxPos);
        player.setPositionOy(oyPos);
        assertEquals(player.getPositionOx(), oxPos);
        assertEquals(player.getPositionOy(), oyPos);
    }

    @Test
    public void scoreTest() {
        int score = random.nextInt();
        player.setScore(score);
        assertEquals(player.getScore(), score);
    }

    @Test
    public void nameTest() {
        String name = "SomeName";
        player.setName(name);
        assertEquals(player.getName(), name);
    }
}