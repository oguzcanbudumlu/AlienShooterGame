package alienshooterserver.alienshooterserverside.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
