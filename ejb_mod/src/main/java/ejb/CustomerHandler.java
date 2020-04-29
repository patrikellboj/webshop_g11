package ejb;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
//det blir deploy-fel om vi inte implementerar local eller remote interface:
public class CustomerHandler implements CustomerHandlerLocal {
//vi ska injicera EntityManager när vår app ligger på en app-server
    //det är bästa sättet att initiera den då

    @PersistenceContext(unitName = "ManyToManyEJB-ejbPU")
    private EntityManager em;


    public void persist(Object object) {
        em.persist(object);
    }

    public List<Product> getProductsfromDb() {
        List<Product> products = new ArrayList<>();
        try {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
            products =  query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public void registerNewOrder(User current, List <Product> confirmedList ){
        Orders newOrder= new Orders(current, confirmedList);
        //LoggHandler.logg(Level.INFO, newOrder.getUser().getUsername());                //det funkar
        //LoggHandler.logg(Level.INFO, confirmedList.get(1).getName());                 //det funkar
        //LoggHandler.logg(Level.INFO, newOrder.getProductList().get(1).getName());     //det funkar
    }


}