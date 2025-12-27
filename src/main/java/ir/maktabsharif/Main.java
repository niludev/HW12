package ir.maktabsharif;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default")) {

            // EntityManager: hameye amaliate DB(actions: Insert, Update, ...) ba in anjam mishe
            // EntityManagerFactory: sazande/karkhune EntityManager

            try (EntityManager entityManager = emf.createEntityManager()) {

            }

        }

    }
}