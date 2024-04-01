package hala.hala.services;

import hala.hala.repository.CardRepository;
import hala.hala.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    @Transactional
    public Optional<Card> updateCard(Card card) {
        Optional<Card> existingCard = cardRepository.findById(card.getId());
        if (existingCard.isPresent()) {
            cardRepository.save(card);
        }
        return existingCard;
    }

    @Transactional
    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }

    public List<Card> getUniqueCards(int count) {
        List<Card> allCards = cardRepository.findAll();
        Collections.shuffle(allCards);
        List<Card> uniqueCards = allCards.subList(0, count / 2);
        List<Card> gameCards = new ArrayList<>();
        for (Card card : uniqueCards) {
            gameCards.add(card);
            gameCards.add(card);
        }
        Collections.shuffle(gameCards);
        return gameCards;
    }
}
