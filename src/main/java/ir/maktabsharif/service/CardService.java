package ir.maktabsharif.service;

import models.Card;
import repository.CardRepository;

import java.util.List;

public class CardService {
    CardRepository cardRepository;

    public CardService (CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card registerCard (String cardNumber, String bankName, Long balance, Long userId) {

        Card card = new Card();

        card.setCardNumber(cardNumber);
        card.setBankName(bankName);
        card.setBalance(balance);
        card.setUserId(userId);

        cardRepository.save(card);

        return card;
    }

    public Card deleteCardByCardNumber(String cardNumber) {

        Card card = cardRepository.findByCardNumber(cardNumber);

        if (card == null) {
            return null;
        }

        cardRepository.deleteCardByCardNumber(cardNumber);
        return card;
    }

    public Card getCardByCardNumber (String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);

        if (card == null) {
//            System.out.println("There is no card with the card number " + cardNumber);
            return null;
        } else {
            return card;
        }
    }

    public List<Card> getCardsByBankName (String bankName) {
        List<Card> cards = cardRepository.findByBankName(bankName);

        if (cards.isEmpty()) {
//            System.out.println("There is no cards with the bank name " + bankName);
            return null;
        }

        return cards;
    }

    public List<Card> getAllCards () {

//        List<Card> allCards = cardRepository.findAll();

        return cardRepository.findAll();
    }
}