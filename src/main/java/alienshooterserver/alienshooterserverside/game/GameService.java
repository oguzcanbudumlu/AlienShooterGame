package alienshooterserver.alienshooterserverside.game;

import alienshooterserver.alienshooterserverside.player.Player;
import alienshooterserver.alienshooterserverside.player.PlayerRepository;
import alienshooterserver.alienshooterserverside.score.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<Game>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    public Optional<Game> getGame(Long gameId) {
        return gameRepository.findById(gameId);
    }

    public void addGame(Game game) {
        gameRepository.save(game);
    }

    public void updateGame(Long gameId, Game game) {
        Optional<Game> temp = gameRepository.findById(gameId);
        if (temp != null) {
            gameRepository.deleteById(gameId);
            gameRepository.save(game);
        }
    }

    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    public List<Score> getScoreBoardAll() {
        List<Score> result = new ArrayList<Score>();

        List<Game> games = new ArrayList<Game>();
        gameRepository.findAll().forEach(games::add);

        long max = -1;
        int index = 0;

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

    public List<Score> getScoreBoardWeekly() {
        List<Score> result = new ArrayList<Score>();

        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusWeeks(1);

        List<Game> games = new ArrayList<Game>();
        gameRepository.findAll().forEach(games::add);
        List<Game> weeklyFilteredGames = new ArrayList<Game>();

        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getDate().isAfter(lastWeek)) {
                weeklyFilteredGames.add(games.get(i));
            }
        }

        long max = -1;
        int index = 0;

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

    public long getNextGameId() {
        long max = 0;
        List<Game> games = new ArrayList<Game>();
        gameRepository.findAll().forEach(games::add);
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getGameId() > max) max = games.get(i).getGameId();
        }
        return max + 1;
    }
}
