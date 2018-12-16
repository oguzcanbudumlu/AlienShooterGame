
package alienshooterserver.alienshooterserverside.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {


    @Autowired
    private PlayerService playerService;

    @RequestMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @RequestMapping("/players/{playerId}")
    public Player getPlayer(@PathVariable long playerId) {
        return playerService.getPlayer(playerId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/players")
    public void addPlayer(@RequestBody Player player) {
        playerService.addPlayer(player);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String registerPlayer(@RequestBody Player player) {
        return playerService.registerPlayer(player);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/players/{playerId}")
    public void updatePlayer(@RequestBody Player player, @PathVariable long playerId) {
        playerService.updatePlayer(playerId, player);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/players/{playerId}")
    public void deletePlayer(@PathVariable long playerId) {
        playerService.deletePlayer(playerId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String logInPlayer(@RequestBody Player player) {
        return playerService.logInPlayer(player);
    }

    @RequestMapping("/playerid/{nickname}")
    public long logInPlayer(@PathVariable String nickname) {
        return playerService.getPlayerId(nickname);
    }

    @RequestMapping("/nextId")
    public long getNextId() {
        return playerService.getNextId();
    }
}
