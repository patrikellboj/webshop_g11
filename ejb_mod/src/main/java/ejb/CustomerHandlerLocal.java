package ejb;

import java.util.List;

public interface CustomerHandlerLocal {

    public List<Product> getProductsfromDb();

    public void registerNewOrder( User current, List <Product> cartList);

    public double calculateTotal(User currentUser, List <Product> cartList );

}
