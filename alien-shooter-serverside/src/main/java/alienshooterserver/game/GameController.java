package alienshooterserver.game;

import alienshooterserver.score.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    //An instance of a GameService is injected here
    @Autowired
    private GameService gameService;

    //When there is a get request through the url ../games
    //It returns all the game objects in the database
    @RequestMapping("/games")
    public List<Game> getAllGames() { return gameService.getAllGames(); }

    //When there is a get request through the url ../games/{gameId}
    //It returns the game object with the gameId
    //returns null if there no such object
    @RequestMapping("/games/{gameId}")
    public Optional<Game> getPlayer(@PathVariable Long gameId) {
        return gameService.getGame(gameId);
    }

    //When there is a post request over the url ../games with body containing a game object
    //it adds the game to the game table in the database
    @RequestMapping(method = RequestMethod.POST, value = "/addgame")
    public void addGame(@RequestBody Game game) {
        System.out.println("in the controller");
        gameService.addGame(game);
    }

    //When there is a put request over the url ../games/{gameId} with body containing a game object
    //The object with the specified gameId will be updated with the passed object over the body.
    @RequestMapping(method = RequestMethod.PUT, value = "/games/{gameId}")
    public void updateGame(@RequestBody Game game, @PathVariable long gameId) {
        gameService.updateGame(gameId, game);
    }

    //When there is a delete request over the url ../games/{gameId}
    //The game with the gameId specified in the path variable will be deleted.
    @RequestMapping(method = RequestMethod.DELETE, value = "/games/{gameId}")
    public void deleteGame(@PathVariable long gameId) {
        gameService.deleteGame(gameId);
    }

    //This is a get request to show the 10 players with the highest scores of all the time.
    @RequestMapping("/scoreboardallthetime")
    public List<Score> getScoreBoardAll() { return gameService.getScoreBoardAll(); }

    //This is a get request to show the 10 players with the highest scores of the last week
    @RequestMapping("/scoreboardweekly")
    public List<Score> getScoreBoardWeekly() { return gameService.getScoreBoardWeekly(); }

    //This is a get request to get the next available gameId
    //We will use it when we have to create a game object.
    @RequestMapping("/getNextGameId")
    public long getNextGameId() { return gameService.getNextGameId(); }
}
