package ejb;

import java.util.List;

public interface AdminHandlerLocal {

    List<User> getUsersFromDB();

    List<Orders> getOrdersOf(String user);
}
