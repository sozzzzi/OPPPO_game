package hala.hala.controllers;

import hala.hala.models.Game;
import hala.hala.models.Lobby;
import hala.hala.services.LobbyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Управление лобби")
@RestController
@RequestMapping("/api/lobbies")
public class LobbyController {
    private final LobbyService lobbyService;

    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @Operation(summary = "Создать новое лобби")
    @PostMapping
    public ResponseEntity<Lobby> createLobby(@Parameter(description = "Объект Lobby для создания") @RequestBody Lobby lobby) {
        Lobby createdLobby = lobbyService.createLobby(lobby);
        return ResponseEntity.ok(createdLobby);
    }

    @Operation(summary = "Удалить лобби по ID")
    @DeleteMapping("/{lobbyId}")
    public ResponseEntity<Void> deleteLobby(@Parameter(description = "ID лобби для удаления") @PathVariable Long lobbyId) {
        lobbyService.deleteLobby(lobbyId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить игроков в лобби")
    @GetMapping("/{lobbyId}/players")
    public ResponseEntity<List<Game>> getPlayersInLobby(@Parameter(description = "ID лобби") @PathVariable Long lobbyId) {
        List<Game> games = lobbyService.getPlayersInLobby(lobbyId);
        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Найти игру по имени лобби")
    @GetMapping("/game/{lobbyName}")
    public ResponseEntity<Game> findGameByLobbyName(@Parameter(description = "Имя лобби") @PathVariable String lobbyName) {
        Optional<Game> game = lobbyService.findGameByLobbyName(lobbyName);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить все активные лобби")
    @GetMapping("/active")
    public ResponseEntity<Optional<List<Lobby>>> findAllActiveLobbies() {
        Optional<List<Lobby>> waitingLobbies = lobbyService.findAllWaitingLobbies();
        return ResponseEntity.ok(waitingLobbies);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/active/{lobbyName}")
    public ResponseEntity<Optional<List<Lobby>>> findWaitingLobbiesByName(@Parameter(description = "Имя лобби") @PathVariable String lobbyName) {
        Optional<List<Lobby>> waitingLobbies = lobbyService.findWaitingLobbiesByName(lobbyName);
        return ResponseEntity.ok(waitingLobbies);
    }

    @GetMapping("/name/{lobbyName}")
    public ResponseEntity<Lobby> findLobbyByName(@PathVariable String lobbyName) {
        Optional<Lobby> lobby = lobbyService.findLobbyByName(lobbyName);
        return lobby.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{lobbyId}")
    public ResponseEntity<Lobby> findLobbyById(@PathVariable Long lobbyId) {
        Optional<Lobby> lobby = lobbyService.findLobbyById(lobbyId);
        return lobby.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
