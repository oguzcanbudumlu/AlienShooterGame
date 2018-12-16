package alienshooterserver.alienshooterserverside;

import alienshooterserver.alienshooterserverside.player.Player;
import alienshooterserver.alienshooterserverside.player.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerRepositoryTests {

	@Autowired
	private PlayerRepository playerRepository;
	Player player0 = new Player(0, "omer", "cetin");
	Player player1 = new Player(1, "omer_", "cetin_");
	Player player2 = new Player(2, "omer*", "cetin*");
	Player player3 = new Player(3, "omer.", "cetin.");

	@Test
	public void findsByIdTest() throws Exception {
		playerRepository.save(player0);
		Optional<Player> foundPlayer = playerRepository.findById(player0.getPlayerId());
		assertEquals(foundPlayer.get().getPlayerId(), player0.getPlayerId());
	}

	@Test
	public void deletesByIdTest() throws Exception {
		Player player0 = new Player(0, "omer", "cetin");
		playerRepository.save(player0);
		playerRepository.deleteById(player0.getPlayerId());
		assertEquals(playerRepository.count(), 0);
	}
}