package alienshooterserver.player;

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

    /**
     * Returns all players in the database
     * @return all players in the database
     */
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<Player>();
        playerRepository.findAll().forEach(players::add);
        return players;
    }

    /**
     * With specific Id, it returns Player who has this Id
     * @param playerId
     * @return Player with given Id
     */
    public Player getPlayer(long playerId) {
        if (playerRepository.findById(playerId) == null) return Optional.of(new Player(-1, "null", "null")).get();
        return playerRepository.findById(playerId).get();
    }

    /**
     * It saves new Player to the database with POST request
     * @param player
     */
    public void addPlayer(Player player) {
        //players.add(player);
        if (player == null) return;
        player.setPlayerId(generateId++);
        player.setPassword(Integer.toString(player.getPassword().hashCode()));
        playerRepository.save(player);
    }

    /**
     * It updates saved Player with PUT request by deleting
     * previous record from the database and saving new record
     * to the database with the same Id.
     * @param playerId
     * @param player
     */
    public void updatePlayer(Long playerId, Player player) {
        Optional<Player> temp = playerRepository.findById(playerId);
        if (temp != null) {
            playerRepository.deleteById(playerId);
            playerRepository.save(player);
        }
    }

    /**
     * it deletes the player with given Id. This function is called
     * with DELETE request.
     * @param playerId
     */
    public void deletePlayer(long playerId) {
        //players.removeIf(t -> t.getPlayerId() == playerId);
        playerRepository.deleteById(playerId);
    }

    /**
     * It provides any client to access the system with her/his
     * nickname and password and if the client is logged in the database
     * with these, s/he can log in successfully. Otherwise, it returns
     * "No Player with <nickname>" or "Wrong Pass" depending on
     * self-explanatory situations. This function is called with
     * GET request.
     * @param nickname
     * @param password
     * @return status about operation
     */
    public String logInPlayer(String nickname, String password) {
        Player temp = playerRepository.findByNickname(nickname);
        if (temp == null) return "No Player with Nickname " + nickname + ".";
        if (temp.getPassword().equals(password)) return "Logged in.";
        else return "Wrong Password.";
    }

    /**
     * It provides any client to access the system. It is called when any
     * request body is provided with POST request. The function queries the database
     * with given Player object to verify whether it is logged in the database. If
     * there does not exist such a Player, it returns "No Player with <nickname>.". If
     * username and password of Player matches with those in the database, it returns
     * "Logged in". If password does not match, it returns "Wrong Password."
     * @param player
     * @return status about login process
     */
    public String logInPlayer(Player player) {
        Player temp = playerRepository.findByNickname(player.getNickname());
        if (temp == null) return "No Such a Player.";
        if (temp.getPassword().equals(player.getPassword())) return "Logged in.";
        else return "Wrong Password.";
    }

    /**
     * The function takes any Player candidate and previously
     * queries the database with nickname of the Player. If it
     * does not exist in the database, it returns "Player Saved.".
     * Otherwise, it returns "Nickname Not Unique.".
     * @param player
     * @return status about registration process and registers player if appropriate
     */
    public String registerPlayer(Player player) {
        Player temp = playerRepository.findByNickname(player.getNickname());
        if (temp == null) {
            player.setPlayerId(generateId++);
            player.setPassword(Integer.toString(player.getPassword().hashCode()));
            playerRepository.save(player);
            return "Player Saved.";
        }
        else return "Nickname Not Unique.";
    }

    /**
     * When we want to create Player, we make use of getNextId()
     * function to determine primary key(its id) of Player.
     * @return next available Id for new Player
     */
    public long getNextId() {
        long max = -1;
        List<Player> players = new ArrayList<Player>();
        playerRepository.findAll().forEach(players::add);
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerId() > max) max = players.get(i).getPlayerId();
        }
        return max + 1;
    }

    /**
     * deletes all the elements in the repository.
     */
    public void deleteAll() {
        playerRepository.deleteAll();
    }

    /**
     * this function is used when we dont know the playerId of the player
     * @param nickname
     * @return playerId of the player with nickname nickname
     */
    public long getPlayerId(String nickname) {
        Player player = playerRepository.findByNickname(nickname);
        return player.getPlayerId();
    }
}
