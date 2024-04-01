package hala.hala.services;

import hala.hala.models.Game;
import hala.hala.models.Lobby;
import hala.hala.repository.GameRepository;
import hala.hala.repository.LobbyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LobbyServiceTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private LobbyService lobbyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createLobby() {
        Lobby lobby = new Lobby();
        when(lobbyRepository.save(any(Lobby.class))).thenReturn(lobby);
        Lobby createdLobby = lobbyService.createLobby(new Lobby());
        assertEquals(lobby, createdLobby);
        verify(lobbyRepository, times(1)).save(any(Lobby.class));
    }

    @Test
    void deleteLobby() {
        Long lobbyId = 1L;
        lobbyService.deleteLobby(lobbyId);
        verify(lobbyRepository, times(1)).deleteById(lobbyId);
    }

    @Test
    void findGameByLobbyName() {
        String lobbyName = "Test Lobby";
        Optional<Game> game = Optional.of(new Game());
        when(gameRepository.findFirstByLobby_LobbyName(lobbyName)).thenReturn(game);
        Optional<Game> foundGame = lobbyService.findGameByLobbyName(lobbyName);
        assertEquals(game, foundGame);
        verify(gameRepository, times(1)).findFirstByLobby_LobbyName(lobbyName);
    }

    @Test
    void findLobbyByName() {
        String lobbyName = "Test Lobby";
        Optional<Lobby> lobby = Optional.of(new Lobby());
        when(lobbyRepository.findByLobbyName(lobbyName)).thenReturn(lobby);
        Optional<Lobby> foundLobby = lobbyService.findLobbyByName(lobbyName);
        assertEquals(lobby, foundLobby);
        verify(lobbyRepository, times(1)).findByLobbyName(lobbyName);
    }
}
