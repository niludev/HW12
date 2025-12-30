package ir.maktabsharif.repository;

import config.ApplicationContext;
import models.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    private final Connection connection;

    public CardRepository() {
        this.connection = ApplicationContext.getInstance().getConnection();
    }


    public Card save(Card card) {

        String sql = "INSERT INTO cards (card_number, bank_name, balance, user_id) VALUES (?, ?, ?, ?)";

        try(PreparedStatement pps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pps.setString(1, card.getCardNumber());
            pps.setString(2, card.getBankName());
            pps.setLong(3, card.getBalance());
            pps.setLong(4, card.getUserId());

//          executeQuery ---> فقط برای select هست
//          برای: INSERT/UPDATE/DELETE
            pps.executeUpdate();

            ResultSet rs = pps.getGeneratedKeys();

//          نتیجه ی rs ستون ها اسم ندارن
            if(rs.next()) {
                card.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return card;
    }

    public List<Card> findByUserId(Long userId) {
        String sql = "SELECT id, card_number, bank_name, balance FROM cards WHERE user_id = ?";

        List<Card> cards = new ArrayList<>();

        try(PreparedStatement pps = connection.prepareStatement(sql)) {

            pps.setLong(1, userId);

            ResultSet rs = pps.executeQuery();


            while (rs.next()) {
                Card card = new Card();

                card.setId(rs.getLong("id"));
                card.setCardNumber(rs.getString("card_number"));
                card.setBankName(rs.getString("bank_name"));
                card.setBalance(rs.getLong("balance"));
                card.setUserId(userId);


                cards.add(card);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }

    public Card mapRowToCard(ResultSet rs) throws SQLException {
        Card card = new Card();

        card.setId(rs.getLong("id"));
        card.setCardNumber(rs.getString("card_number"));
        card.setBankName(rs.getString("bank_name"));
        card.setBalance(rs.getLong("balance"));
        card.setUserId(rs.getLong("user_id"));

        return card;
    }

//    می شه card_number هم درخواست کرد با اینکه داریمش
    public Card findByCardNumber(String cardNumber) {
        String sql = "SELECT id, card_number, bank_name, balance, user_id FROM cards WHERE card_number = ?";

        try(PreparedStatement pps = connection.prepareStatement(sql)) {

            pps.setString(1, cardNumber);

            ResultSet rs = pps.executeQuery();

            if (rs.next()) {
                return mapRowToCard(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Card> findByBankName (String bankName) {
        String sql = "SELECT id, card_number, bank_name, balance, user_id FROM cards WHERE bank_name = ?";
        List<Card> cards = new ArrayList<>();

        try(PreparedStatement pps = connection.prepareStatement(sql)) {

            pps.setString(1, bankName);

            ResultSet rs = pps.executeQuery();

            while (rs.next()) {

//                Card card = mapRowToCard(rs);
                cards.add(mapRowToCard(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }

    public void updateBalance (Long cardId, Long newBalance) {
        String sql = "UPDATE cards SET balance = ? WHERE id = ?";

        try(PreparedStatement pps = connection.prepareStatement(sql)) {

            pps.setLong(1, newBalance);
            pps.setLong(2, cardId);

            pps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCardByCardNumber (String cardNumber) {
        String sql = "DELETE FROM cards WHERE card_number = ?";

        try(PreparedStatement pps = connection.prepareStatement(sql)) {

            pps.setString(1, cardNumber);

            pps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Card> findAll () {
        String sql = "SELECT id, card_number, bank_name, balance, user_id FROM cards";

        List<Card> cards = new ArrayList<>();

        try(PreparedStatement pps = connection.prepareStatement(sql)) {

            ResultSet rs = pps.executeQuery();

            while (rs.next()) {
//                Card card = mapRowToCard(rs);
                cards.add(mapRowToCard(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }







}
