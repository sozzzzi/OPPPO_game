package hala.hala.services;

import hala.hala.models.Game;
import hala.hala.models.Lobby;
import hala.hala.repository.GameRepository;
import hala.hala.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LobbyService {
    private final LobbyRepository lobbyRepository;
    private final GameRepository gameRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, GameRepository gameRepository) {
        this.lobbyRepository = lobbyRepository;
        this.gameRepository = gameRepository;
    }

    public Optional<Lobby> findLobbyById(long id) {
        return lobbyRepository.findById(id);
    }

    @Transactional
    public Lobby createLobby(Lobby lobby) {
        return lobbyRepository.save(lobby);
    }

    @Transactional
    public void deleteLobby(Long lobbyId) {
        lobbyRepository.deleteById(lobbyId);
    }

    public List<Game> getPlayersInLobby(Long lobbyId) {
        Optional<Game> gameOpt = gameRepository.findByLobby_Id(lobbyId);
        List<Game> games = new ArrayList<>();
        gameOpt.ifPresent(games::add);
        return games;
    }

    public Optional<Game> findGameByLobbyName(String lobbyName) {
        return gameRepository.findFirstByLobby_LobbyName(lobbyName);
    }

    public Optional<Lobby> findLobbyByName(String lobbyName) {
        return lobbyRepository.findByLobbyName(lobbyName);
    }

    public Optional<List<Lobby>> findAllWaitingLobbies() {
        return lobbyRepository.findAllWaitingLobbies();
    }

    public Optional<List<Lobby>> findWaitingLobbiesByName(String lobbyName) {
        return lobbyRepository.findWaitingLobbiesByName(lobbyName);
    }
}

