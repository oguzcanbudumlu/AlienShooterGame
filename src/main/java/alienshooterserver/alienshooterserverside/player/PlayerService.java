package alienshooterserver.alienshooterserverside.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    // private List<Player> players = new ArrayList<Player>();

    public List<Player> getAllPlayers() {
        //return players;
        List<Player> players = new ArrayList<Player>();
        playerRepository.findAll().forEach(players::add);
        return players;
    }

    public Optional<Player> getPlayer(long playerId) {
        //return players.stream().filter(t -> t.getPlayerId() == playerId).findFirst().get();
        return playerRepository.findById(playerId);
    }

    public void addPlayer(Player player) {
        //players.add(player);
        playerRepository.save(player);
    }

    public void updatePlayer(Long playerId, Player player) {
        /*
        for (int i = 0; i < players.size(); i++) {
            Player temp = players.get(i);
            if (temp.getPlayerId() == playerId) {
                players.set(i, player);
                return;
            }
        }
        */
        Optional<Player> temp = playerRepository.findById(playerId);
        if (temp != null) {
            playerRepository.deleteById(playerId);
            playerRepository.save(player);
        }

    }

    public void deletePlayer(long playerId) {
        //players.removeIf(t -> t.getPlayerId() == playerId);
        playerRepository.deleteById(playerId);
    }
}
