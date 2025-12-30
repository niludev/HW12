package ir.maktabsharif.repository;


import ir.maktabsharif.config.JpaUtil;
import ir.maktabsharif.models.Card;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.sql.*;
import java.util.List;

public class CardRepository {

    public Card save(Card card) {

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(card);
            tx.commit();
            return card;

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;

        } finally {
            em.close();
        }
    }

    public List<Card> findByUserId(Long userId) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "select c from Card c where c.user.id = :userId",
                    Card.class
            ).setParameter("userId", userId)
                    .getResultList();

        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();
        }
    }

    public Card findByCardNumber(String cardNumber) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "select c from Card c where c.cardNumber = :cardNumber",
                    Card.class
            ).setParameter("cardNumber", cardNumber)
                    .getSingleResultOrNull();

        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();
        }
    }

    public List<Card> findByBankName (String bankName) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "select c from Card c where c.bankName = :bankName",
                            Card.class
                    ).setParameter("bankName", bankName)
                    .getResultList();

        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();
        }
    }

    public void updateBalance (Long cardId, Long newBalance) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Card card = em.find(Card.class, cardId);

            if (card != null) {
                card.setBalance(newBalance);
            }

//            dg persist nemikonim bala ba find bordimesh tuye halate managed
//            va chizi ham be DB nemikhaim ezafe konim
//            em.persist(card);

            tx.commit();

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;

        } finally {
            em.close();
        }
    }

    public void deleteCardByCardNumber (String cardNumber) {

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Card card = em.createQuery(
                    "select c from Card c where c.cardNumber = :cardNumber",
                    Card.class
            ).setParameter("cardNumber", cardNumber)
                    .getSingleResultOrNull();
//                    .getResultList();  // getSingleResultOrNull male Hibernate 6 hast na JPQL  ---> vali chon ma dar pom ino darim: hibernate-core mishe azash estefade kard


            if (card != null) {
                em.remove(card);
            }

            tx.commit();

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;

        } finally {
            em.close();
        }
    }


    public List<Card> findAll () {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery("select c from Card c", Card.class)
                    .getResultList();

        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();
        }
    }







}
