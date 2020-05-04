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

    @Override
    public void registerNewOrder(User currentUser, List <Product> cartList ){
        Orders newOrder= new Orders(currentUser, cartList);
        persist(newOrder);
    }

    @Override
    public double calculateTotal(User currentUser, List <Product> cartList ){
        double total = 0;
            for (Product product: cartList)
        total = total + product.getPrice();
        if (currentUser.getRole() == Role.PREMIUM_CUSTOMER) {
            return total * 0.90;
        }
        return total;
    }


}