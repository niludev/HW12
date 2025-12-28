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

                // state remove:
                User user = entityManager.find(User.class, 5L);

                // in mire tu halate remove va da zamane tarakonesh vaghti commit mikonim az DB pak mishe:
                entityManager.remove(user);


                entityManager.getTransaction().commit();
            }

        }

    }
}