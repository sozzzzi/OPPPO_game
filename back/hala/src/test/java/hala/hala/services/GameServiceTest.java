package hala.hala.services;

import hala.hala.models.Card;
import hala.hala.models.Game;
import hala.hala.models.UserEntity;
import hala.hala.repository.CardRepository;
import hala.hala.repository.GameRepository;
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

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteGame() {
        Long gameId = 1L;
        gameService.deleteGame(gameId);
        verify(gameRepository, times(1)).deleteById(gameId);
    }

    @Test
    void getCardsInGame() {
        Long gameId = 1L;
        List<Card> cards = new ArrayList<>();
        when(cardRepository.findByGame_Id(gameId)).thenReturn(cards);
        List<Card> retrievedCards = gameService.getCardsInGame(gameId);
        assertEquals(cards, retrievedCards);
        verify(cardRepository, times(1)).findByGame_Id(gameId);
    }

    @Test
    void setCardCount() {
        Long gameId = 1L;
        int cardCount = 10;
        Game game = new Game();
        Optional<Game> gameOptional = Optional.of(game);
        when(gameRepository.findById(gameId)).thenReturn(gameOptional);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        Optional<Game> updatedGameOptional = gameService.setCardCount(gameId, cardCount);
        assertEquals(gameOptional, updatedGameOptional);
        assertEquals(cardCount, game.getCardCount());
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, times(1)).save(any(Game.class));
    }
}
