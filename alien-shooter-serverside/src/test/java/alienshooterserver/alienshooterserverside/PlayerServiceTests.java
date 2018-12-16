package alienshooterserver.alienshooterserverside;

import alienshooterserver.alienshooterserverside.player.Player;
import alienshooterserver.alienshooterserverside.player.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerServiceTests<player0> {

    @Autowired
    private PlayerService playerService;
    Player player0 = new Player(0,"omer", "cetin");
    Player player1 = new Player(1,"omer_", "cetin_");
    Player player2 = new Player(2,"omer*", "cetin*");
    Player player3 = new Player(3,"omer.", "cetin.");
    Player player4 = new Player(4, "omer*", "cetin*");

    @Test
    public void deleteAllTest() throws Exception {
        playerService.deleteAll();
        playerService.registerPlayer(player0);
        playerService.registerPlayer(player1);
        playerService.deleteAll();
        assertEquals(playerService.getAllPlayers().size(), 0);
    }

    @Test
    public void getAllPlayerTest() throws Exception {
        playerService.deleteAll();
        playerService.registerPlayer(player0);
        playerService.registerPlayer(player1);
        playerService.registerPlayer(player2);
        playerService.registerPlayer(player3);
        assertEquals(playerService.getAllPlayers().size(), 4);
    }

    @Test
    public void deleteTest() throws Exception {
        playerService.deleteAll();
        System.out.println(playerService.getAllPlayers().size());
        playerService.registerPlayer(player0);
        playerService.deletePlayer(player0.getPlayerId());
        assertEquals(playerService.getAllPlayers().size(), 0);
    }

    @Test
    public void registerWithSameNameTest() throws Exception {
        playerService.deleteAll();
        System.out.println(playerService.getAllPlayers().size());
        playerService.registerPlayer(player2);
        playerService.registerPlayer(player4);
        assertEquals(playerService.getAllPlayers().size(), 1);
    }

    @Test
    public void logInPlayerRegisteredTest() throws Exception {
        playerService.deleteAll();
        playerService.registerPlayer(player1);
        assertEquals(playerService.logInPlayer(player1), "Logged in.");
    }

    @Test
    public void logInPlayerUnregisteredTest() throws Exception {
        playerService.deleteAll();
        System.out.println(playerService.getAllPlayers().size());
        assertEquals(playerService.logInPlayer(player0), "No Player with Nickname " + player0.getNickname() + ".");
    }

    @Test
    public void logInPlayerWrongPasswordTest() throws Exception {
        playerService.deleteAll();
        playerService.registerPlayer(player4);
        player4.setPassword("wrong");
        assertEquals(playerService.logInPlayer(player4), "Wrong Password.");
    }

    @Test
    public void getNextIdTest() throws Exception {
        playerService.deleteAll();
        assertEquals(playerService.getNextId(), 0);
    }

    @Test
    public void updatePlayerTest() throws Exception {
        playerService.deleteAll();
        playerService.registerPlayer(player0);
        playerService.updatePlayer(player0.getPlayerId(), player1);
        Player foundPlayer = playerService.getPlayer(player1.getPlayerId());
        //System.out.println(playerService.getAllPlayers().get(0).getPlayerId());
        assertEquals(foundPlayer.getPlayerId(), player1.getPlayerId());
    }
}