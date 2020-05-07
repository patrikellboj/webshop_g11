package ejb;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
            User user1 = new User("Göran","Customersson","Customergatan 9","customer", "customer123", "goran@mail.com", Role.CUSTOMER, 0);
            persist(user1);
            User user2 = new User("Fredrik","Premiumsson","Preiumvägen 7","premium", "premium123", "fredrik@mail.com", Role.PREMIUM_CUSTOMER, 500);
            persist(user2);
            User user3 = new User("Albert","Adminsson", "Adminvägen 67", "admin", "admin123", "admin@mail.com", Role.ADMIN, 0);
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
        User user;

        try {
            // LOWER = till små bokstäver.
            Query query = em.createQuery("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:userName) AND u.password = :password");
            query.setParameter("userName", userName);
            query.setParameter("password", password);

            // casta om det vi får tillbaka till en user
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            LoggHandler.logg(Level.INFO, "No result from db");
            e.printStackTrace();
            return null;
        } catch (NonUniqueResultException e) {
            LoggHandler.logg(Level.INFO, "Error: Found more than one user");
            e.printStackTrace();
            return null;
        }
        return user;
    }


    @Override
    public boolean addNewUser(
            String firstName,
            String lastName,
            String address,
            String userName,
            String password,
            String email) {
        populateDBWithUsers();
        User user;
        List<User> resultFromDb = new ArrayList<>();
        boolean userAdded = false;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:userName)", User.class);
            query.setParameter("userName", userName);
            resultFromDb = query.getResultList();
            if (resultFromDb.size() < 1) {
                user = new User(firstName, lastName, address, userName, password, email, Role.CUSTOMER, 0);
                persist(user);
                userAdded = true;
            }
        } catch (Exception e) {
            LoggHandler.logg(Level.INFO, "Exception!");
            e.printStackTrace();
        }
        return userAdded;
    }




    public void uppdateTotalAmountAndRole(String username, double orderTotal){
        User user = new User();
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.username =:username");
            query.setParameter("username", username);
            user = (User) query.getSingleResult();
            user.setTotalAmount(user.getTotalAmount()+orderTotal);

            if(user.getRole() == Role.CUSTOMER && user.getTotalAmount() >= 500)
                user.setRole(Role.PREMIUM_CUSTOMER);
            em.merge(user);



        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace();
        }
        //bra att returnera status ifrån metoder istället för att bara göra dem till void

    }

}