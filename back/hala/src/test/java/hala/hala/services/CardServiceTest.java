package hala.hala.services;

import hala.hala.models.Card;
import hala.hala.repository.CardRepository;
import hala.hala.services.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateCard() {
        Card card = new Card();
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        Card createdCard = cardService.createCard(card);
        assertEquals(card, createdCard);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testUpdateCard() {
        Card card = new Card();
        card.setId(1L);
        Optional<Card> optionalCard = Optional.of(card);
        when(cardRepository.findById(1L)).thenReturn(optionalCard);
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Optional<Card> updatedCard = cardService.updateCard(card);

        assertEquals(optionalCard, updatedCard);
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testDeleteCard() {
        Long cardId = 1L;
        cardService.deleteCard(cardId);
        verify(cardRepository, times(1)).deleteById(cardId);
    }
}
