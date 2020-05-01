package web;

import ejb.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Named(value = "adminController")
@SessionScoped
public class AdminController implements Serializable {

    @EJB
    AdminHandlerLocal adminHandlerLocal;
    private List<Orders> selectedUserOrders = new ArrayList<>();
    private String selectedUserUsername;
    private String selectedUserPassword;
    private Enum selectedUserRole;
    private boolean renderOrders = false;

    public List <User> getUsers() {
        return adminHandlerLocal.getUsersFromDB();
    }

    public void getUserInfo(String username){
        for(User user : getUsers()) {
            if(user.getUsername().equals(username)) {
                selectedUserUsername = user.getUsername();
                selectedUserPassword = user.getPassword();
                selectedUserRole = user.getRole();
                renderOrders = true;
                selectedUserOrders = adminHandlerLocal.getOrdersOf(username);
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

    public List<Orders> getSelectedUserOrders() {
        return selectedUserOrders;
    }
}