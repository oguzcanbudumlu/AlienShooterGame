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
    public Optional<Player> getPlayer(@PathVariable long playerId) {
        return playerService.getPlayer(playerId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/players")
    public void addPlayer(@RequestBody Player player) {
        playerService.addPlayer(player);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/players/{playerId}")
    public void updatePlayer(@RequestBody Player player, @PathVariable long playerId) {
        playerService.updatePlayer(playerId, player);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/players/{playerId}")
    public void deletePlayer(@PathVariable long playerId) {
        playerService.deletePlayer(playerId);
    }
}
