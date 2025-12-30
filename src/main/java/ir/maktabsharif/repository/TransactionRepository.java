package ir.maktabsharif.repository;

import config.ApplicationContext;
import models.Transaction;

import java.sql.*;

public class TransactionRepository {
    private final Connection connection;

    public TransactionRepository () {
        this.connection = ApplicationContext.getInstance().getConnection();
    }

    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transactions (from_card_id, to_card_id, amount, fee, type, status, description, batch_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement pps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pps.setLong(1, transaction.getFromCardId());
            pps.setLong(2, transaction.getToCardId());
            pps.setLong(3, transaction.getAmount());
            pps.setLong(4, transaction.getFee());
            pps.setString(5, transaction.getType());
            pps.setString(6, transaction.getStatus());
            pps.setString(7, transaction.getDescription());

            if (transaction.getBatchId() == null) {
                pps.setNull(8, Types.BIGINT);
            } else {
                pps.setLong(8, transaction.getBatchId());
            }

//            created_at : DB ----> CURRENT_TIMESTAMP

            pps.executeUpdate();

            ResultSet rs = pps.getGeneratedKeys();

            if (rs.next()) {
                transaction.setId(rs.getLong(1));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transaction;
    }

//    TODO:
//    List<Transaction> findByCardId(Long cardId)
//    List<Transaction> findAll()


}
