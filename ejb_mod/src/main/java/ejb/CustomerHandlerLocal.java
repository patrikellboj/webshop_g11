package ejb;

import java.util.List;

public interface CustomerHandlerLocal {

    List<Product> getProductsfromDb();

    void registerNewOrder(User currentUser, List <Product> cartList);

    double calculateTotal(User currentUser, List <Product> cartList );

}
