package hala.hala.repository;

import hala.hala.models.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {
    Optional<Lobby> findByLobbyName(String lobbyName);

    @Query("SELECT l FROM Lobby l WHERE l.lobbyName LIKE %:lobbyName% AND EXISTS (SELECT g FROM Game g WHERE g.lobby = l AND g.gameInProgress = false AND g.gameEnded IS NULL)")
    Optional<List<Lobby>> findWaitingLobbiesByName(@Param("lobbyName") String lobbyName);

    @Query("SELECT l FROM Lobby l WHERE EXISTS (SELECT g FROM Game g WHERE g.lobby = l AND g.gameInProgress = false AND g.gameEnded IS NULL)")
    Optional<List<Lobby>> findAllWaitingLobbies();
}
