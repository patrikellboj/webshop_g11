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
    @Column(name = "user_name", nullable = false) // Får inte vara null
    private String username;
    @Column(name = "password", nullable = false) // Får inte vara null
    private String password;
    private Role role;
    @Column(name = "totalAmount", nullable = false) // Får inte vara null
    private double totalAmount;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy="user")
    private List<Orders> ordersList = new ArrayList<>();



    public User() {
    }

    public User(String userName, String password, Role role, double totalAmount) {
        this.username = userName;
        this.password = password;
        this.role = role;
        this.totalAmount = totalAmount;

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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
