package hala.hala.controllers;

import hala.hala.models.Move;
import hala.hala.services.MoveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Управление ходами")
@RestController
@RequestMapping("/api/moves")
public class MoveController {
    private final MoveService moveService;

    @Autowired
    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    @Operation(summary = "Создать новый ход")
    @PostMapping
    public ResponseEntity<Move> createMove(@Parameter(description = "Объект Move для создания") @RequestBody Move move) {
        Move createdMove = moveService.createMove(move);
        return ResponseEntity.ok(createdMove);
    }

    @Operation(summary = "Удалить ход по ID")
    @DeleteMapping("/{moveId}")
    public ResponseEntity<Void> deleteMove(@Parameter(description = "ID хода для удаления") @PathVariable Long moveId) {
        moveService.deleteMove(moveId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить ходы в игре")
    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Move>> getMovesInGame(@Parameter(description = "ID игры") @PathVariable Long gameId) {
        List<Move> moves = moveService.getMovesInGame(gameId);
        return ResponseEntity.ok(moves);
    }

    @Operation(summary = "Найти ход по ID пользователя")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Move> findMoveByUserId(@Parameter(description = "ID пользователя") @PathVariable Long userId) {
        Optional<Move> move = moveService.findMoveByUserId(userId);
        return move.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

