package ir.maktabsharif;

import ir.maktabsharif.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default")) {

            // EntityManager: hameye amaliate DB(actions: Insert, Update, ...) ba in anjam mishe
            // EntityManagerFactory: sazande/karkhune EntityManager

            try (EntityManager entityManager = emf.createEntityManager()) {

                // har EntityManager be ye EntityTransaction vabaste hast
                // Tarakonesh haro ham bayad khodemun modiriat konim

                EntityTransaction entityTransaction = entityManager.getTransaction();
                entityTransaction.begin();

                User user = new User();
                user.setUsername("admin");
                user.setPassword("123");

                // baraye insert kardan
                // yeki az rah haii ke instance Entity ro mibare tu halate manage (method haye dg: find,remove va retrieve)
                entityManager.persist(user);

                // aya ye chizi dakhele entityManager hast ya na (aya tu halate manage gharar dare ya na)
                // javab e in mishe true chon inja rafte tu halate manage: entityManager.persist(user);
                // pas dg SELECT neminevise (query nemizane) va mige UPDATE, chon entityManager daratesh hanuz ru halate manage.
                User fetchUser = entityManager.find(User.class, 1L);
                boolean contains = entityManager.contains(fetchUser);
                System.out.println(fetchUser);
                System.out.println("contains: " + contains);

                // sabte taghirat dar DB
                entityTransaction.commit();

                // detach: az halate manage dg darmiad
                entityManager.detach(fetchUser);
                boolean contains1 = entityManager.contains(fetchUser);
                System.out.println("contains: " + contains1);
            }

        }

    }
}