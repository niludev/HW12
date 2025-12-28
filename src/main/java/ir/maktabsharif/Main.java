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
                User user = entityManager.find(User.class, 4L);


                // remove  ------ persist ------> manage
                // manage  ------ remove ------> remove

                // in mire tu halate remove va da zamane tarakonesh vaghti commit mikonim az DB pak mishe:
                entityManager.remove(user);

                // dobare mire tu halate manage va dg pak nemishe
                // dg baraye remove neshane gozari nashode
                entityManager.persist(user);



                // detach  ------ merge ------> manage
                // manage  ------ detach ------> detach

                // mamulan baraye update azash estefade mishe

                // hala az detach mirim dobare be manage in kar ro ba method e merge anjam midim
                // age ye chizi ke tu halate remove hast ro behesh pass bedim error mikhorim

                // farghesh ba persist:
                // merge khodesh return type dare, un return type mire tu halate managed na khode user
                // vali dar persist chon java call by value hast va persit return type sh void hast dare be un reference e user eshare mikone pas khode un object (user) mire tu halate manage
                // pas age mikhaim az merge estefade konim va hamun object (user) ro bebarim tu halate manage va na return type sh ro bayad:
//              // khurujie merge ro dobare assign konim be hamun object (user)
                user = entityManager.merge(user);

                entityManager.getTransaction().commit();
            }

        }

    }
}