package alienshooterserver.alienshooterserverside;

import alienshooterserver.alienshooterserverside.game.Game;
import alienshooterserver.alienshooterserverside.game.GameRepository;
import alienshooterserver.alienshooterserverside.player.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;

    Player player = new Player("omer", "cetin");
    Game game0 = new Game((long) 0, (long) 0, LocalDate.now(), player);
    Game game1 = new Game((long) 1, (long) 0, LocalDate.now(), player);
    Game game2 = new Game((long) 2, (long) 0, LocalDate.now(), player);
    Game game3 = new Game((long) 3, (long) 0, LocalDate.now(), player);

    @Test
    public void findsByIdTest() throws Exception {
        gameRepository.save(game0);
        Optional<Game> foundGame = gameRepository.findById(game0.getGameId());
        assertEquals(foundGame.get().getGameId(), game0.getGameId());
    }

    @Test
    public void deletesByIdTest() throws Exception {
        gameRepository.save(game0);
        gameRepository.deleteById(game0.getGameId());
        assertEquals(gameRepository.count(), 0);
    }
}