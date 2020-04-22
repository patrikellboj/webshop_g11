package ejb;

import javax.ejb.Local;

/**
 *
 * @author eyvind
 */
@Local
public interface UserHandlerLocal {

    void addNewUser(String userName, String password);

    User login(String userName, String password);

    void populateDBWithUsers();

}