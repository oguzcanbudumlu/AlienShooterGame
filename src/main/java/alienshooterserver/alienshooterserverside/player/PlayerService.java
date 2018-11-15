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

    public int generateId = 0;
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
        player.setPlayerId(generateId++);
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

    public String logInPlayer(String nickname, String password) {
        Player temp = playerRepository.findByNickname(nickname);
        if (temp == null) return "No Player with Nickname " + nickname + ".";
        if (temp.getPassword().equals(password)) return "Logged in.";
        else return "Wrong Password.";
    }

    public String logInPlayer(Player player) {
        Player temp = playerRepository.findByNickname(player.getNickname());
        if (temp == null) return "No Player with Nickname " + player.getNickname() + ".";
        if (temp.getPassword().equals(player.getPassword())) return "Logged in.";
        else return "Wrong Password.";
    }

    public String registerPlayer(Player player) {
        Player temp = playerRepository.findByNickname(player.getNickname());
        if (temp == null) {
            player.setPlayerId(generateId++);
            playerRepository.save(player);
            return "Player Saved.";
        }
        else return "Nickname Not Unique.";
    }

}
