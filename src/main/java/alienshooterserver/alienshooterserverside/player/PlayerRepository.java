package alienshooterserver.alienshooterserverside.player;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    public Player findByNickname(String nickname);
}