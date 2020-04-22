package ejb;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;


@Entity
@Table(name = "USER")

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private Role role;
    @OneToMany(cascade=PERSIST, mappedBy="user")
    private List<Orders> ordersList =new ArrayList();



    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;

    }

    //-------------------------------------

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

 /*    //Logiken

    public User check(String usernameInput, String passwordInput) {
        for (User user : users) {
            if (usernameInput.equalsIgnoreCase(user.getUsername()) && passwordInput.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }*/


/*
    public String register(String usernameInput, String passwordInput) {
        for (User user : users) {
            if (usernameInput.equalsIgnoreCase(user.getUsername()))
                return "Username alredy exists";
        }
        users.add(new User(usernameInput, passwordInput, Role.CUSTOMER));
        return  "User added successfully";
    }

 */


}
