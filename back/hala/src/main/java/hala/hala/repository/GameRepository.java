package hala.hala.repository;

import hala.hala.models.Game;
import hala.hala.models.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByLobby_Id(Long lobbyId);
    Optional<Game> findFirstByLobby_LobbyName(String lobbyName);
}
