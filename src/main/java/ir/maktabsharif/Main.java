package ir.maktabsharif;

import ir.maktabsharif.models.User;
import jakarta.persistence.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default")) {

            try (EntityManager entityManager = emf.createEntityManager()) {

                findUserByUsername(entityManager, "admin");
                findUserByUsername(entityManager, "admin1");

            }

        }

    }

    //nabayad EntityManager ro behesh pas bedim vali inja baraye mesal daddim:
    public static void findUserByUsername(EntityManager entityManager, String username) {

        // :my_user_param or u.username = ?1
        TypedQuery<User> typedQuery = entityManager.createQuery(
                "select u from User u where u.username = :my_user_param",
                User.class
        );

        typedQuery.setParameter("my_user_param", username);
        System.out.println(typedQuery.getSingleResultOrNull());
    }
}