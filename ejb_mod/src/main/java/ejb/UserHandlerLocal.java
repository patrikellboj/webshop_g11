package ejb;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserHandlerLocal {

    boolean addNewUser(String firstName,
                       String lastName,
                       String address,
                       String userName,
                       String password,
                       String email);

    User login(String userName, String password);

    void populateDBWithUsers();

    void populateDBWithProducts();

    void uppdateTotalAmountAndRole(String username , double orderTotal);
}