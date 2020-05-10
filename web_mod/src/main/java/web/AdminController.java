package web;

import ejb.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "adminController")
@SessionScoped
public class AdminController implements Serializable {

    @EJB
    AdminHandlerLocal adminHandlerLocal;
    private List <Product> products = new ArrayList<>();
    private boolean renderOrders = false;
    private int orderCounter = 0;

    private String selectedFirstName;
    private String selectedLastName;
    private String selectedAddress;
    private String selectedUserUsername;
    private String selectedUserPassword;
    private String selectedEmail;
    private Enum selectedUserRole;




    public List <User> getUsers() {
        return adminHandlerLocal.getUsersFromDB();
    }

    public List<Orders> getOrders() {
        return adminHandlerLocal.getOrdersOf(selectedUserUsername);
    }

    public void getUserInfo(String username) {
        for (User user : getUsers()) {
            if (user.getUsername().equals(username)) {
                selectedFirstName = user.getFirstName();
                selectedLastName = user.getLastName();
                selectedAddress = user.getAddress();
                selectedUserUsername = user.getUsername();
                selectedUserPassword = user.getPassword();
                selectedEmail = user.getEmail();
                selectedUserRole = user.getRole();

                renderOrders = true;
            }
        }
        orderCounter = 0;
        products.clear();
    }

    public int getOrderCounter(){
        orderCounter++;
        return orderCounter;
    }

    public String getSelectedUserUsername() {
        return selectedUserUsername;
    }

    public String getSelectedUserPassword() {
        return selectedUserPassword;
    }

    public Enum getSelectedUserRole() {
        return selectedUserRole;
    }

    public boolean getRenderOrders() {
        return renderOrders;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getSelectedFirstName() {
        return selectedFirstName;
    }

    public String getSelectedLastName() {
        return selectedLastName;
    }

    public String getSelectedAddress() {
        return selectedAddress;
    }

    public String getSelectedEmail() {
        return selectedEmail;
    }

    public void setProducts(List<Product> products) {
        orderCounter = 0;
        this.products = products;
    }

    public String logout() {
        selectedUserUsername = "";
        selectedUserPassword = "";
        selectedUserRole = null;
        renderOrders = false;
        getOrders().clear();
        getProducts().clear();
        return "index";
    }
}