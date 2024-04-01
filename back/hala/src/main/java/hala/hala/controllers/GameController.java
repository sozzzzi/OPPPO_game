package hala.hala.controllers;

import hala.hala.models.Card;
import hala.hala.models.Game;
import hala.hala.models.UserEntity;
import hala.hala.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Управление играми")
@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Создать новую игру")
    @PostMapping
    public ResponseEntity<Game> createGame(@Parameter(description = "Объект Game для создания") @RequestBody Game game) {
        System.out.println("HI BOB!");
        System.out.println(game);
        Game createdGame = gameService.createGame(game);
        return ResponseEntity.ok(createdGame);
    }

    @Operation(summary = "Удалить игру по ID")
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@Parameter(description = "ID игры для удаления") @PathVariable Long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить карты в игре")
    @GetMapping("/{gameId}/cards")
    public ResponseEntity<List<Card>> getCardsInGame(@Parameter(description = "ID игры") @PathVariable Long gameId) {
        List<Card> cards = gameService.getCardsInGame(gameId);
        return ResponseEntity.ok(cards);
    }

    @Operation(summary = "Получить игроков в лобби")
    @GetMapping("/lobby/{lobbyId}/players")
    public ResponseEntity<List<UserEntity>> getPlayersInLobby(@Parameter(description = "ID лобби") @PathVariable Long lobbyId) {
        List<UserEntity> userEntities = gameService.getPlayersInLobby(lobbyId);
        return ResponseEntity.ok(userEntities);
    }

    @Operation(summary = "Установить количество карт в игре")
    @PutMapping("/{gameId}/cardCount/{cardCount}")
    public ResponseEntity<Game> setCardCount(@Parameter(description = "ID игры") @PathVariable Long gameId, @Parameter(description = "Количество карт") @PathVariable int cardCount) {
        Optional<Game> game = gameService.setCardCount(gameId, cardCount);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable Long gameId) {
        Optional<Game> game = gameService.findGameById(gameId);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить все игры")
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.findAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/lobby/id/{lobbyId}")
    public ResponseEntity<Game> findGameByLobbyId(@PathVariable Long lobbyId) {
        Optional<Game> game = gameService.findGameByLobbyId(lobbyId);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/lobby/name/{lobbyName}")
    public ResponseEntity<Game> findGameByLobbyName(@PathVariable String lobbyName) {
        Optional<Game> game = gameService.findGameByLobbyName(lobbyName);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Добавить пользователя в игру")
    @PostMapping("/{gameId}/users/{userId}")
    public ResponseEntity<Game> addUserToGame(@Parameter(description = "ID игры") @PathVariable Long gameId, @Parameter(description = "ID пользователя") @PathVariable Long userId) {
        Game updatedGame = gameService.addUserToGame(gameId, userId);
        return ResponseEntity.ok(updatedGame);
    }
}

