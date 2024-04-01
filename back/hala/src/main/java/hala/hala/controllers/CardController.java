package hala.hala.controllers;

import hala.hala.models.Card;
import hala.hala.services.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Управление картами")
@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Создать новую карту")
    @PostMapping
    public ResponseEntity<Card> createCard(@Parameter(description = "Объект Card для создания") @RequestBody Card card) {
        Card createdCard = cardService.createCard(card);
        return ResponseEntity.ok(createdCard);
    }

    @Operation(summary = "Обновить существующую карту")
    @PutMapping
    public ResponseEntity<Card> updateCard(@Parameter(description = "Объект Card для обновления") @RequestBody Card card) {
        Optional<Card> updatedCard = cardService.updateCard(card);
        return updatedCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить карту по ID")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@Parameter(description = "ID карты для удаления") @PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить уникальные карты")
    @GetMapping("/unique/{count}")
    public ResponseEntity<List<Card>> getUniqueCards(@Parameter(description = "Количество уникальных карт") @PathVariable int count) {
        List<Card> cards = cardService.getUniqueCards(count);
        return ResponseEntity.ok(cards);
    }
}