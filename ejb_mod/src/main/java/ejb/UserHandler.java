package ejb;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
//det blir deploy-fel om vi inte implementerar local eller remote interface:
public class UserHandler implements UserHandlerLocal {
//vi ska injicera EntityManager när vår app ligger på en app-server
    //det är bästa sättet att initiera den då

    @PersistenceContext(unitName = "ManyToManyEJB-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public void populateDBWithUsers() {
        Query query = em.createQuery("SELECT u FROM User u");
        int sizeOfUsersTable = query.getResultList().size();
        if (sizeOfUsersTable < 3) {
            User user1 = new User("customer", "customer123", Role.CUSTOMER);
            persist(user1);
            User user2 = new User("premium", "premium123", Role.PREMIUM_CUSTOMER);
            persist(user2);
            User user3 = new User("admin", "admin123", Role.ADMIN);
            persist(user3);
        }
    }

    public void populateDBWithProducts() {
        Query query = em.createQuery("SELECT p FROM Product p");
        int sizeOfProductsTable = query.getResultList().size();
        if (sizeOfProductsTable < 3) {
            Product prod1 = new Product("Dillchips", "Smakfulla dillchips från OLW!", 20.0D);
            persist(prod1);
            Product prod2 = new Product("Sourcream", "Delikata sorucreamchips från OLW!", 20.0D);
            persist(prod2);
            Product prod3 = new Product("Grillchips", "Krispiga grillchips från OLW!", 20.0D);
            persist(prod3);
        }

    }

    @Override
    public User login(String userName, String password) {
        populateDBWithUsers();
        populateDBWithProducts();
        User user = new User();

        try {
            // LOWER = till små bokstäver.
            Query query = em.createQuery("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:userName) AND u.password = :password");
            query.setParameter("userName", userName);
            query.setParameter("password", password);

            // casta om det vi får tillbaka till en user
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            // TODO: 2020-04-22 Ändra till logger metod eller ta bort sout
            System.out.println("No result from db");
            e.printStackTrace();
            return null;
        } catch (NonUniqueResultException e) {
            // TODO: 2020-04-22 Ändra till logger metod eller ta bort sout
            System.out.println("Found more than one user");
            e.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public String addNewUser(String userName, String password) {
        User user = new User(userName, password, Role.CUSTOMER);
        persist(user);
        return "User added successfully";
    }


}