package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Stateless
public class AdminHandler implements AdminHandlerLocal{

    @PersistenceContext(unitName = "ManyToManyEJB-ejbPU")
    private EntityManager em;

    @Override
    public List<User> getUsersFromDB() {
        List <User> users = new ArrayList<>();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            users = query.getResultList();
        } catch (Exception e) {
            LoggHandler.logg(Level.INFO, "Exception!");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<Orders> getOrdersOf(String user) {
        List <Orders> orders = new ArrayList<>();
        try {
            TypedQuery<Orders> query = em.createQuery("SELECT o FROM Orders o", Orders.class);
            for(Orders order : query.getResultList()) {
                if(order.getUser().getUsername().equals(user)) {
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            LoggHandler.logg(Level.INFO, "Exception!");
            e.printStackTrace();
        }
        return orders;
    }
}
