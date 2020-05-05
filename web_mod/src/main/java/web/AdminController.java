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
    private String selectedUserUsername;
    private String selectedUserPassword;
    private Enum selectedUserRole;
    private boolean renderOrders = false;
    private int orderCounter = 0;

    public List <User> getUsers() {
        return adminHandlerLocal.getUsersFromDB();
    }

    public List<Orders> getOrders() {
        return adminHandlerLocal.getOrdersOf(selectedUserUsername);
    }

    public void getUserInfo(String username) {
        for (User user : getUsers()) {
            if (user.getUsername().equals(username)) {
                selectedUserUsername = user.getUsername();
                selectedUserPassword = user.getPassword();
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