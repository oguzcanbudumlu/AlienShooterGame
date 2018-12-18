package alienshooterserver.game;

import alienshooterserver.score.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    //An instance of a GameRepository class is injected here
    @Autowired
    private GameRepository gameRepository;

    public long generateId = 0;

    //This function returns all the game rows in the database in a list
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<Game>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    //it takes a paramater which is gameId and returns the game object with that gameId
    public Optional<Game> getGame(Long gameId) {
        return gameRepository.findById(gameId);
    }

    //it basically adds the game object into the database.
    public void addGame(Game game) {
        System.out.println("here");
        game.setGameId(generateId++);
        gameRepository.save(game);
    }

    //it takes two arguments which are a gameId and a game object
    //it updates the game object with the gameId with the game object passed as a parameter
    public void updateGame(Long gameId, Game game) {
        Optional<Game> temp = gameRepository.findById(gameId);
        if (temp != null) {
            gameRepository.deleteById(gameId);
            gameRepository.save(game);
        }
    }

    //it deletes the game object with the gameId
    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    //it returns a list of Score which contains the top 10 players' name and score of all the time
    public List<Score> getScoreBoardAll() {
        List<Score> result = new ArrayList<Score>();

        //all game rows in the database is passed to a list named games
        List<Game> games = new ArrayList<Game>();
        gameRepository.findAll().forEach(games::add);

        long max = -1;
        int index = 0;

        //this block finds the game object with the highest score
        //then it adds the score of that game object and the player name into the Score object
        //the score object is added into result list and the game object is removed from games list
        //this operation above is done 10 times or number of objects in the game list times
        for (int j = 0; j < 10 && games.size() > 0; j++) {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).getScore() > max) {
                    max = games.get(i).getScore();
                    index = i;
                }
            }
            result.add(new Score(games.get(index).getPlayer().getNickname(), max));
            int finalIndex = index;
            games.removeIf(t -> t == games.get(finalIndex));
            max = -1;
        }
        return result;
    }

    //it returns a list of Score which contains the top 10 players' name and score of the last week
    public List<Score> getScoreBoardWeekly() {
        List<Score> result = new ArrayList<Score>();

        //finds the current date and the date a week before
        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusWeeks(1);

        //all the game rows in the database are passed into the list
        List<Game> games = new ArrayList<Game>();
        gameRepository.findAll().forEach(games::add);
        //the games player in the last week will be passed into this list.
        List<Game> weeklyFilteredGames = new ArrayList<Game>();

        //the games are filtered in such a way that the games played in the last will be added
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getDate().isAfter(lastWeek)) {
                weeklyFilteredGames.add(games.get(i));
            }
        }

        long max = -1;
        int index = 0;

        //this block finds the game object with the highest score
        //then it adds the score of that game object and the player name into the Score object
        //the score object is added into result list and the game object is removed from weeklyFilteredGames list
        //this operation above is done 10 times or (less) number of objects in the game list times
        for (int j = 0; j < 10 && weeklyFilteredGames.size() > 0; j++) {
            for (int i = 0; i < weeklyFilteredGames.size(); i++) {
                if (weeklyFilteredGames.get(i).getScore() > max) {
                    max = weeklyFilteredGames.get(i).getScore();
                    index = i;
                }
            }
            result.add(new Score(weeklyFilteredGames.get(index).getPlayer().getNickname(), max));
            int finalIndex = index;
            weeklyFilteredGames.removeIf(t -> t == weeklyFilteredGames.get(finalIndex));
            max = -1;
        }

        return result;
    }

    //this function is used to generate unique gameIds
    //basically it finds the max gameId and returns the max gameId plus 1.
    public long getNextGameId() {
        long max = 0;
        List<Game> games = new ArrayList<Game>();
        gameRepository.findAll().forEach(games::add);
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getGameId() > max) max = games.get(i).getGameId();
        }
        return max + 1;
    }

    /**
     * deletes all the elements in the repository.
     */
    public void deleteAllGames() {
        gameRepository.deleteAll();
    }
}
