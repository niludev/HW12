package ir.maktabsharif.repository;

import ir.maktabsharif.config.JpaUtil;
import ir.maktabsharif.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.sql.*;

public class UserRepository  {

    public User save(User user) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Insert inja etefagh miofte davaghe bad az commit
            em.persist(user);

            tx.commit();

            return user;

        } catch (RuntimeException e) {

            // yani in tarakonesh ro bargardun (age hanuz tarakonesh baze yani begin shode vali commit nashode),
            // nemikham in taghirat nesfe nime zakhire beshe --> rollback()
            if (tx.isActive()) tx.rollback();
            throw e;

        } finally {
            // baraye inke memory leak nakone, che movafagh bud che nabud beband connection ro
            em.close();
        }
    }

    public boolean existsByUsername(String username) {

        EntityManager em = JpaUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();


        try {
//            tx.begin();

            Long count = em.createQuery(
                    "SELECT count(u) from User u where u.username = :username",
                    Long.class
            ).setParameter("username", username)
                    .getSingleResult();

//            tx.commit();

            return count > 0;

//            Database connection or query problem
        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();
        }
    }

    public User findById(Long userId) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT u FROM User u WHERE u.id = :userId",
                            User.class)
                    .setParameter("userId", userId)
                    .getSingleResultOrNull();

        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();
        }
    }


        public User findByUsername(String username) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
//                    "SELECT u.id, u.username, u.password FROM User u WHERE username = :username",
                    "SELECT u FROM User u WHERE username = :username",
                    User.class)
                    .setParameter("username", username)
                    .getSingleResultOrNull();

        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();
        }
    }
}
