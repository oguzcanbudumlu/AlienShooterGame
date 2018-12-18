package alienshooterserver.game;

import org.springframework.data.repository.CrudRepository;

//CrudRepository provides generic CRUD operations
public interface GameRepository extends CrudRepository<Game, Long> {}