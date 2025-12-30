package ir.maktabsharif.service;



import ir.maktabsharif.models.Card;
import ir.maktabsharif.models.User;
import ir.maktabsharif.repository.CardRepository;
import ir.maktabsharif.repository.UserRepository;

import java.util.List;

public class CardService {
    CardRepository cardRepository;
    UserRepository userRepository;

    public CardService (CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Card registerCard (String cardNumber, String bankName, Long balance, Long userId) {

        User user = userRepository.findById(userId);

        if (user == null) {
            System.out.println("User not found");
            return null;
        }

        Card card = new Card();

        card.setCardNumber(cardNumber);
        card.setBankName(bankName);
        card.setBalance(balance);
        card.setUser(user);

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