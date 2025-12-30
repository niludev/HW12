package ir.maktabsharif.repository;


import ir.maktabsharif.config.JpaUtil;
import ir.maktabsharif.models.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.*;
import java.util.List;

public class TransactionRepository {

    public Transaction save(Transaction transaction) {

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(transaction);
            tx.commit();
            return transaction;

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;

        } finally {
            em.close();
        }
    }

//    List<Transaction> findByCardId(Long cardId)
//    List<Transaction> findAll()

//    public List<Transaction> findByCardId(Long cardId) {
//        EntityManager em = JpaUtil.getEntityManager();
//
//        try {
//            return em.createQuery(
//                            "SELECT t FROM Transaction t WHERE t.fromCard.id = :cardId OR t.toCard.id = :cardId",
//                            Transaction.class
//                    ).setParameter("cardId", cardId)
//                    .getResultList();
//
//        } finally {
//            em.close();
//        }
//    }
//
//    public List<Transaction> findAll() {
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            return em.createQuery("SELECT t FROM Transaction t", Transaction.class)
//                    .getResultList();
//        } finally {
//            em.close();
//        }
//    }

}
