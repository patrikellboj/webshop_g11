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

    public List <User> getUsers() {
        return adminHandlerLocal.getUsersFromDB();
    }

    public List<Orders> getOrders() {
        return adminHandlerLocal.getOrdersOf(selectedUserUsername);
    }

    public void getUserInfo(String username){
        for(User user : getUsers()) {
            if(user.getUsername().equals(username)) {
                selectedUserUsername = user.getUsername();
                selectedUserPassword = user.getPassword();
                selectedUserRole = user.getRole();
                renderOrders = true;
            }
        }
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
        this.products = products;
    }
}