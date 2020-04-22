package ejb;

import javax.ejb.Local;

/**
 *
 * @author eyvind
 */
@Local
public interface UserHandlerLocal {

    void populate();

    User check(String usernameInput, String passwordInput);

    String register(String usernameInput, String passwordInput);



}