package ejb;

import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author eyvind
 */
@Local
public interface UserHandlerLocal {

    boolean addNewUser(String userName, String password);

    User login(String userName, String password);

    void populateDBWithUsers();

    void populateDBWithProducts();
}