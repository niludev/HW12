package ir.maktabsharif;

import ir.maktabsharif.models.User;
import jakarta.persistence.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default")) {

            try (EntityManager entityManager = emf.createEntityManager()) {

                entityManager.getTransaction().begin();


                User user = entityManager.find(User.class, 4L);

                entityManager.persist(user);

                entityManager.getTransaction().commit();
            }

        }

    }
}