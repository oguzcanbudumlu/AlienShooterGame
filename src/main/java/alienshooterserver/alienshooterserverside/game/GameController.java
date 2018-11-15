package alienshooterserver.alienshooterserverside.game;

import alienshooterserver.alienshooterserverside.score.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping("/games")
    public List<Game> getAllGames() { return gameService.getAllGames(); }

    @RequestMapping("/games/{gameId}")
    public Optional<Game> getPlayer(@PathVariable Long gameId) {
        return gameService.getGame(gameId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/games")
    public void addGame(@RequestBody Game game) {
        gameService.addGame(game);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/games/{gameId}")
    public void updateGame(@RequestBody Game game, @PathVariable long gameId) {
        gameService.updateGame(gameId, game);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/games/{gameId}")
    public void deleteGame(@PathVariable long gameId) {
        gameService.deleteGame(gameId);
    }

    @RequestMapping("/scoreboardallthetime")
    public List<Score> getScoreBoardAll() { return gameService.getScoreBoardAll(); }

    @RequestMapping("/scoreboardweekly")
    public List<Score> getScoreBoardWeekly() { return gameService.getScoreBoardWeekly(); }

    @RequestMapping("/getNextGameId")
    public long getNextGameId() { return gameService.getNextGameId(); }
}
