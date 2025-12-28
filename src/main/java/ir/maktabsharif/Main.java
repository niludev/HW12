package ir.maktabsharif;

import ir.maktabsharif.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default")) {

            try (EntityManager entityManager = emf.createEntityManager()) {


//                EntityTransaction entityTransaction = entityManager.getTransaction();
//                entityTransaction.begin();

                // peyda kardane hameye user ha
                // ba query JPQL: native nist --> bar hasbe esme class e java va esme field haye java
                List<User> resultList = entityManager.createQuery(
                        "select u from User u where u.username is not null",
                        User.class
                ).getResultList();

                // in bayad ba result yeki bashe:
                // "select u.username from User u where u.username is not null",
                // String.class

                resultList.forEach(user -> System.out.println(user.getId() + " contains in em: " + entityManager.contains(user)));

//                entityTransaction.commit();
            }

        }

    }
}