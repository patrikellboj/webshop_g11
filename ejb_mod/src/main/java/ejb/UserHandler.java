package ejb;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author eyvind
 */
@Stateless
//det blir deploy-fel om vi inte implementerar local eller remote interface:
public class UserHandler implements UserHandlerLocal {
//vi ska injicera EntityManager när vår app ligger på en app-server
    //det är bästa sättet att initiera den då

    @PersistenceContext(unitName = "ManyToManyEJB-ejbPU")
    private EntityManager em;
    private List<User> users = new ArrayList<>();

    public void populate(){
        users.add(new User("kund","kund123", Role.CUSTOMER));
        users.add(new User("premium","premium123", Role.PREMIUM_CUSTOMER));
        users.add(new User("admin","admin123", Role.ADMIN));
    }


    public User check(String usernameInput, String passwordInput) {
        for (User user : users) {
            if (usernameInput.equalsIgnoreCase(user.getUsername()) && passwordInput.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }


    public String register(String usernameInput, String passwordInput) {
        for (User user : users) {
            if (usernameInput.equalsIgnoreCase(user.getUsername()))
                return "Username alredy exists";
        }
        users.add(new User(usernameInput, passwordInput, Role.CUSTOMER));
        return  "User added successfully";
    }


}